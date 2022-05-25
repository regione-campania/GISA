<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.util.Iterator"%>
<%@page import="ext.aspcfs.modules.apiari.base.Operatore"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,ext.aspcfs.modules.apiari.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="ListaAttivitaDaValidare"
	class="ext.aspcfs.modules.apiari.base.OperatoreList" scope="request" />

<jsp:useBean id="ListaNuoviApiari"
	class="ext.aspcfs.modules.apiari.base.StabilimentoList" scope="request" />
<jsp:useBean id="VariazioniUbicazioni"
	class="ext.aspcfs.modules.apiari.base.StabilimentoList" scope="request" />
<jsp:useBean id="VariazioniCensimento"
	class="ext.aspcfs.modules.apiari.base.StabilimentoList" scope="request" />
<jsp:useBean id="VariazioniDetentore"
	class="ext.aspcfs.modules.apiari.base.StabilimentoList" scope="request" />


<jsp:useBean id="TipoAttivitaApi"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<jsp:useBean id="ApicolturaSottospecie"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ApicolturaModalita"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ApicolturaClassificazione"
	class="org.aspcfs.utils.web.LookupList" scope="request" />

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />




<%@ include file="../initPage.jsp"%>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp"%>



<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/tableJqueryFilterApiNotifiche.js"></script>
	






<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>



<link rel="stylesheet" type="text/css"
	href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css"
	href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>

<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/tableJqueryFilterApiNotifiche.js"></script>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />

<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/gestoreCodiceFiscale.js"></script>
<SCRIPT src="javascript/apiari_search_aziende.js"></SCRIPT>
<script type="text/javascript" src="dwr/interface/ApicolturaRemote.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>




<script>

RegExp.escape = function(text) {
    return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
}
String.prototype.replaceAll = function(search, replace) {
    return this.replace(new RegExp(RegExp.escape(search),'g'), replace);
};

var tipoPraticaIN;
var idApiarioIN ;
function notificaPratica(tipoPratica,idApiario)
{
	/*
	* possibili valori per tipoPratica :
		1. VU = Variazione Ubicazione
		2. VC = Variazione Censimento
		3. VD = Variazione Detentore
		4. NA = Nuovo Apiario
	*
	*/
	tipoPraticaIN = tipoPratica ;
	idApiarioIN = idApiario;
	ApicolturaRemote.notificaApiari(tipoPratica,idApiario,notificaCallback);
	
	
}
function notificaCallback(value)
{
	
	if (value=='OK')
		{
		
		$("#"+tipoPraticaIN+idApiarioIN).remove();
		}
	}
	
	
	

 

$(function () {
	 $( "#searchAzienda" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"ASSEGNA CODICE UNIVOCO NAZIONALE",
	        width:650,
	        height:300,
	        draggable: false,
	        modal: true,
	        position: 'top',
	        open:function(){$("#searchAziendaFieldinput").val("");},
	        buttons:{
	        	 "Salva": function() {alert('submit') ;} ,
	        	 "Esci" : function() { $(this).dialog("close");
	        	 $("html,body").animate({scrollTop: 0}, 500, function(){});
	        	 }
	        	
	        },
	        show: {
	            effect: "blind",
	            duration: 1000
	        },
	        hide: {
	            effect: "explode",
	            duration: 1000
	        }
	       
	    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
});

function generaCodice()
{
	$( '#searchAzienda' ).dialog('open');

}

$(function() {
	  
    $( "#searchAziendaField" ).combobox();
    
 
});
</script>



<body onload="resizeGlobalItemsPane('hide')">


	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td>Variazioni</td>
		</tr>
	</table>

	
	<fieldset>
		<legend>
			LISTA NUOVI APIARI
			<%=ListaNuoviApiari.size()%></legend>



		<table class="tablesorter">

			<thead>
				<tr class="tablesorter-headerRow" role="row">
					
					<th
						aria-label="DETENTORE: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						data-placeholder="FILTRO PER DENOMINAZIONE"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">DENOMINAIONE</div></th>
							
							<th
						aria-label="DETENTORE: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						data-placeholder="FILTRO PER CUN"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">CUN</div></th>
									
					<th
						aria-label="DETENTORE: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						data-placeholder="FILTRO PER DETENTORE"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">DETENTORE</div></th>

					<th
						aria-label="CF DETENTORE ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER CF DETENTORE"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">CF DETENTORE</th>
					<th
						aria-label="CLASSIFICAZIONE ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER CLASSIFICAZIONE"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">CLASSIFICAZIONE</th>
					<th
						aria-label="SOTTOSPECIE ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER SOTTOSPECIE"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">SOTTOSPECIE</th>
					<th
						aria-label="MODALITA ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER MODALITA"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">MODALITA</th>

					<th
						aria-label="NUM ALVEARI ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						CLASS="filter-false tablesorter-header"><div
							class="tablesorter-header-inner">NUM ALVEARI</th>
					<th
						aria-label="NUM SCIAMI No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						class="filter-false tablesorter-header"><div
							class="tablesorter-header-inner">NUM SCIAMI / NUCLEI</div></th>

					<th
						aria-label="DATA INIZIO: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						class="filter-false tablesorter-header"><div
							class="tablesorter-header-inner">DATA INIZIO</div></th>
					<th
						aria-label="DATA CHIUSURA No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						class="filter-false tablesorter-header"><div
							class="tablesorter-header-inner">DATA CHIUSURA</div></th>
					<th
						aria-label="COMUNE No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						data-placeholder="FILTRO PER COMUNE UBICAZIONE"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">UBICAZIONE</div></th>
							
								<th
						aria-label="INDIRIZZO No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0" data-placeholder=""
						class="filter-false tablesorter-header"><div
							class="tablesorter-header-inner">STATO BDN</div></th>
							
					<th
						aria-label="INDIRIZZO No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0" data-placeholder=""
						class="filter-false tablesorter-header"><div
							class="tablesorter-header-inner">NOTIFICA</div></th>



				</tr>
			</thead>
			<tbody aria-relevant="all" aria-live="polite" id="bodyNA">

				<%
					Iterator<Stabilimento> itStab = ListaNuoviApiari.iterator();
					if (ListaNuoviApiari.size() > 0) {
						while (itStab.hasNext()) {
							Stabilimento thisStab = itStab.next();
				%>
				<tr id="NA<%=thisStab.getIdStabilimento()%>">
					<td><%=toHtml2(thisStab.getOperatore().getRagioneSociale())%></td>
					<td><%=toHtml2(thisStab.getOperatore().getCodiceAzienda())%></td>
					<td><%=toHtml2(thisStab.getDetentore().getCognome()) + " "
							+ toHtml2(thisStab.getDetentore().getNome())%></td>
					<td><%=toHtml2(thisStab.getDetentore().getCodFiscale())%></td>
					<td><%=toHtml2(ApicolturaClassificazione.getSelectedValue(thisStab
							.getIdApicolturaClassificazione()))%></td>
					<td><%=toHtml2(ApicolturaSottospecie.getSelectedValue(thisStab.getIdApicolturaSottospecie()))%></td>
					<td><%=toHtml2(ApicolturaModalita.getSelectedValue(thisStab.getIdApicolturaModalita()))%></td>
					<td><%=thisStab.getNumAlveari()%></td>
					<td><%=thisStab.getNumSciami()%></td>

					<td><%=toDateasString(thisStab.getDataApertura())%></td>
					<td><%=toDateasString(thisStab.getDataChiusura())%></td>

					<td><%=thisStab.getSedeOperativa().getDescrizioneComune() + " - "
							+ thisStab.getSedeOperativa().getVia()%></td>

				<td> <%=thisStab.isSincronizzatoBdn()==true ? "<img src=\"images/verde.gif\">" : "<img src=\"images/rosso.gif\">" %></td>
					<td> <input type="checkbox"
						onclick="notificaPratica('NA',<%=thisStab.getIdStabilimento()%>)">
					</td>

				</tr>
				<%
					}
					} else {
				%>
				<tr>
					<td colspan="11">NESSUNA NUOVA REGISTRAZIONE DI APIARI NEL
						PROPRIO TERRITORIO</td>
				</tr>
				<%
					}
				%>

			</tbody>
		</table>


		<br>
		<br>

		<fieldset>
			<legend>VARIAZIONI UBICAZIONI</legend>



			<table class="tablesorter">

				<thead>
					<tr class="tablesorter-headerRow" role="row">



						<th
						aria-label="DETENTORE: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						data-placeholder="FILTRO PER DENOMINAZIONE"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">DENOMINAIONE</div></th>
							
							<th
						aria-label="DETENTORE: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						data-placeholder="FILTRO PER CUN"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">CUN</div></th>
							
						<th
							aria-label="DETENTORE: No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							data-placeholder="FILTRO PER DETENTORE"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">DETENTORE</div></th>

						<th
							aria-label="CF DETENTORE ( filter-match ): No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="1"
							data-placeholder="FILTRO PER CF DETENTORE"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">CF DETENTORE</th>
						<th
							aria-label="CLASSIFICAZIONE ( filter-match ): No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="1"
							data-placeholder="FILTRO PER CLASSIFICAZIONE"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">CLASSIFICAZIONE</th>
						<th
							aria-label="SOTTOSPECIE ( filter-match ): No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="1"
							data-placeholder="FILTRO PER SOTTOSPECIE"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">SOTTOSPECIE</th>
						<th
							aria-label="MODALITA ( filter-match ): No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="1"
							data-placeholder="FILTRO PER MODALITA"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">MODALITA</th>

						<th
							aria-label="NUM ALVEARI ( filter-match ): No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="1"
							CLASS="filter-false tablesorter-header"><div
								class="tablesorter-header-inner">NUM ALVEARI</th>
						<th
							aria-label="NUM SCIAMI No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							class="filter-false tablesorter-header"><div
								class="tablesorter-header-inner">NUM SCIAMI / NUCLEI</div></th>

						<th
							aria-label="DATA INIZIO: No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							class="filter-false tablesorter-header"><div
								class="tablesorter-header-inner">DATA INIZIO</div></th>
						<th
							aria-label="DATA CHIUSURA No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							class="filter-false tablesorter-header"><div
								class="tablesorter-header-inner">DATA CHIUSURA</div></th>
						<th
							aria-label="COMUNE No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							data-placeholder="FILTRO PER COMUNE UBICAZIONE"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">UBICAZIONE</div></th>
						<th
							aria-label="INDIRIZZO No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							data-placeholder="" class="filter-false tablesorter-header"><div
								class="tablesorter-header-inner">NOTIFICA</div></th>



					</tr>
				</thead>
				<tbody aria-relevant="all" aria-live="polite" id="bodyVU">

					<%
						Iterator<Stabilimento> itCens = VariazioniUbicazioni.iterator();
						if (VariazioniUbicazioni.size() > 0) {
							while (itCens.hasNext()) {
								Stabilimento thisStab = itCens.next();
					%>
					<tr id="VU<%=thisStab.getIdStabilimento()%>">
					<td><%=toHtml2(thisStab.getOperatore().getRagioneSociale())%></td>
					<td><%=toHtml2(thisStab.getOperatore().getCodiceAzienda())%></td>
						<td><%=toHtml2(thisStab.getDetentore().getCognome()) + " "
							+ toHtml2(thisStab.getDetentore().getNome())%></td>
						<td><%=toHtml2(thisStab.getDetentore().getCodFiscale())%></td>
						<td><%=toHtml2(ApicolturaClassificazione.getSelectedValue(thisStab
							.getIdApicolturaClassificazione()))%></td>
						<td><%=toHtml2(ApicolturaSottospecie.getSelectedValue(thisStab.getIdApicolturaSottospecie()))%></td>
						<td><%=toHtml2(ApicolturaModalita.getSelectedValue(thisStab.getIdApicolturaModalita()))%></td>
						<td><%=thisStab.getNumAlveari()%></td>
						<td><%=thisStab.getNumSciami()%></td>

						<td><%=toDateasString(thisStab.getDataApertura())%></td>
						<td><%=toDateasString(thisStab.getDataChiusura())%></td>

						<td><%=thisStab.getSedeOperativa().getDescrizioneComune() + " - "
							+ thisStab.getSedeOperativa().getVia()%></td>


						<td><input type="checkbox"
							onclick="notificaPratica('VU',<%=thisStab.getIdStabilimento()%>)">

						</td>

					</tr>
					<%
						}
						} else {
					%>
					<tr>
						<td colspan="11">NESSUNA NUOVA VARIAZIONE UBICAZIONE DI
							APIARI NEL PROPRIO TERRITORIO</td>
					</tr>
					<%
						}
					%>

				</tbody>
			</table>


		</fieldset>
		<br>
		<br> <br>
		<br>

		<fieldset>
			<legend>VARIAZIONI CENSIMENTI</legend>



			<table class="tablesorter">

				<thead>
					<tr class="tablesorter-headerRow" role="row">
					
					<th
						aria-label="DETENTORE: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						data-placeholder="FILTRO PER DENOMINAZIONE"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">DENOMINAIONE</div></th>
							
							<th
						aria-label="DETENTORE: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						data-placeholder="FILTRO PER CUN"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">CUN</div></th>

						<th
							aria-label="DETENTORE: No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							data-placeholder="FILTRO PER DETENTORE"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">DETENTORE</div></th>

						<th
							aria-label="CF DETENTORE ( filter-match ): No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="1"
							data-placeholder="FILTRO PER CF DETENTORE"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">CF DETENTORE</th>
						<th
							aria-label="CLASSIFICAZIONE ( filter-match ): No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="1"
							data-placeholder="FILTRO PER CLASSIFICAZIONE"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">CLASSIFICAZIONE</th>
						<th
							aria-label="SOTTOSPECIE ( filter-match ): No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="1"
							data-placeholder="FILTRO PER SOTTOSPECIE"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">SOTTOSPECIE</th>
						<th
							aria-label="MODALITA ( filter-match ): No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="1"
							data-placeholder="FILTRO PER MODALITA"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">MODALITA</th>

						<th
							aria-label="NUM ALVEARI ( filter-match ): No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="1"
							CLASS="filter-false tablesorter-header"><div
								class="tablesorter-header-inner">NUM ALVEARI</th>
						<th
							aria-label="NUM SCIAMI No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							class="filter-false tablesorter-header"><div
								class="tablesorter-header-inner">NUM SCIAMI / NUCLEI</div></th>

						<th
							aria-label="DATA INIZIO: No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							class="filter-false tablesorter-header"><div
								class="tablesorter-header-inner">DATA INIZIO</div></th>
						<th
							aria-label="DATA CHIUSURA No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							class="filter-false tablesorter-header"><div
								class="tablesorter-header-inner">DATA CHIUSURA</div></th>
						<th
							aria-label="COMUNE No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							data-placeholder="FILTRO PER COMUNE UBICAZIONE"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">UBICAZIONE</div></th>
						<th
							aria-label="INDIRIZZO No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							data-placeholder="" class="filter-false tablesorter-header"><div
								class="tablesorter-header-inner">NOTIFICA</div></th>



					</tr>
				</thead>
				<tbody aria-relevant="all" aria-live="polite" id="bodyVC">

					<%
						Iterator itVarCens = VariazioniCensimento.iterator();
						

						if (VariazioniCensimento.size() > 0) {
							while (itVarCens.hasNext()) {
								Stabilimento thisStab = (Stabilimento) itVarCens.next();
								
					%>
					<tr id="VC<%=thisStab.getIdStabilimento()%>">
					<td><%=toHtml2(thisStab.getOperatore().getRagioneSociale())%></td>
					<td><%=toHtml2(thisStab.getOperatore().getCodiceAzienda())%></td>
					
						<td><%=toHtml2(thisStab.getDetentore().getCognome()) + " "
							+ toHtml2(thisStab.getDetentore().getNome())%></td>
						<td><%=toHtml2(thisStab.getDetentore().getCodFiscale())%></td>
						<td><%=toHtml2(ApicolturaClassificazione.getSelectedValue(thisStab
							.getIdApicolturaClassificazione()))%></td>
						<td><%=toHtml2(ApicolturaSottospecie.getSelectedValue(thisStab.getIdApicolturaSottospecie()))%></td>
						<td><%=toHtml2(ApicolturaModalita.getSelectedValue(thisStab.getIdApicolturaModalita()))%></td>
						<td><%=thisStab.getNumAlveari()%></td>
						<td><%=thisStab.getNumSciami()%></td>

						<td><%=toDateasString(thisStab.getDataApertura())%></td>
						<td><%=toDateasString(thisStab.getDataChiusura())%></td>

						<td><%=thisStab.getSedeOperativa().getDescrizioneComune() + " - "
							+ thisStab.getSedeOperativa().getVia()%></td>


						<td><input type="checkbox"
							onclick="notificaPratica('VC',<%=thisStab.getIdStabilimento()%>)">
						</td>

					</tr>
					<%
						}
						} else {
					%>
					<tr>
						<td colspan="11">NESSUNA NUOVA VARIAZIONE CENSIMENTI DI
							APIARI NEL PROPRIO TERRITORIO</td>
					</tr>
					<%
						}
					%>

				</tbody>
			</table>


		</fieldset>

		<br>
		<br>

		<fieldset>
			<legend>VARIAZIONI DETENTORE</legend>




<table class="tablesorter"><thead>


			<table class="tablesorter">

				<thead>
					<tr class="tablesorter-headerRow" role="row">
						
						
						
						<th
						aria-label="DETENTORE: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						data-placeholder="FILTRO PER DENOMINAZIONE"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">DENOMINAIONE</div></th>
							
							<th
						aria-label="DETENTORE: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						data-placeholder="FILTRO PER CUN"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">CUN</div></th>
							
							
						<th
							aria-label="DETENTORE: No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							data-placeholder="FILTRO PER DETENTORE"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">DETENTORE</div></th>

						<th
							aria-label="CF DETENTORE ( filter-match ): No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="1"
							data-placeholder="FILTRO PER CF DETENTORE"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">CF DETENTORE</th>
						<th
							aria-label="CLASSIFICAZIONE ( filter-match ): No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="1"
							data-placeholder="FILTRO PER CLASSIFICAZIONE"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">CLASSIFICAZIONE</th>
						<th
							aria-label="SOTTOSPECIE ( filter-match ): No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="1"
							data-placeholder="FILTRO PER SOTTOSPECIE"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">SOTTOSPECIE</th>
						<th
							aria-label="MODALITA ( filter-match ): No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="1"
							data-placeholder="FILTRO PER MODALITA"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">MODALITA</th>

						<th
							aria-label="NUM ALVEARI ( filter-match ): No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="1"
							CLASS="filter-false tablesorter-header"><div
								class="tablesorter-header-inner">NUM ALVEARI</th>
						<th
							aria-label="NUM SCIAMI No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							class="filter-false tablesorter-header"><div
								class="tablesorter-header-inner">NUM SCIAMI / NUCLEI</div></th>

						<th
							aria-label="DATA INIZIO: No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							class="filter-false tablesorter-header"><div
								class="tablesorter-header-inner">DATA INIZIO</div></th>
						<th
							aria-label="DATA CHIUSURA No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							class="filter-false tablesorter-header"><div
								class="tablesorter-header-inner">DATA CHIUSURA</div></th>
						<th
							aria-label="COMUNE No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							data-placeholder="FILTRO PER COMUNE UBICAZIONE"
							class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
								class="tablesorter-header-inner">UBICAZIONE</div></th>
						<th
							aria-label="INDIRIZZO No sort applied, activate to apply an ascending sort"
							aria-sort="none" style="-moz-user-select: none;"
							unselectable="on" aria-controls="table" aria-disabled="false"
							role="columnheader" scope="col" tabindex="0" data-column="0"
							data-placeholder="" class="filter-false tablesorter-header"><div
								class="tablesorter-header-inner">NOTIFICA</div></th>



					</tr>
				</thead>
				<tbody aria-relevant="all" aria-live="polite" id="bodyVD">

					<%
		 itStab = VariazioniDetentore.iterator();
	if (VariazioniDetentore.size()>0)
	{
		while (itStab.hasNext())
		{
			Stabilimento thisStab = itStab.next();
			
			%>
					<tr id="VD<%=thisStab.getIdStabilimento()%>">
					<td><%=toHtml2(thisStab.getOperatore().getRagioneSociale())%></td>
					<td><%=toHtml2(thisStab.getOperatore().getCodiceAzienda())%></td>
						<td><%=toHtml2(thisStab.getDetentore().getCognome() ) + " "+toHtml2(thisStab.getDetentore().getNome()) %></td>
						<td><%=toHtml2(thisStab.getDetentore().getCodFiscale())%></td>
						<td><%=toHtml2(ApicolturaClassificazione.getSelectedValue(thisStab.getIdApicolturaClassificazione())) %></td>
						<td><%=toHtml2(ApicolturaSottospecie.getSelectedValue(thisStab.getIdApicolturaSottospecie())) %></td>
						<td><%=toHtml2(ApicolturaModalita.getSelectedValue(thisStab.getIdApicolturaModalita())) %></td>
						<td><%=thisStab.getNumAlveari() %></td>
						<td><%=thisStab.getNumSciami() %></td>

						<td><%=toDateasString(thisStab.getDataApertura())%></td>
						<td><%=toDateasString(thisStab.getDataChiusura()) %></td>

						<td><%=thisStab.getSedeOperativa().getDescrizioneComune() +" - "+ thisStab.getSedeOperativa().getVia() %></td>


						<td><input type="checkbox" onclick="notificaPratica('VD',<%=thisStab.getIdStabilimento()%>)">
						</td>

					</tr>
					<%
			
		}}
	else
	{
		%>
					<tr>
						<td colspan="11">NESSUNA NUOVA VARIAZIONE DENTENTORE DI
							APIARI NEL PROPRIO TERRITORIO</td>
					</tr>
					<%
	}
	%>

				</tbody>
			</table>


		</fieldset>


		<div id="searchAzienda">
			<div id="messaggioErrore"></div>
		


<h3>Codice Univoco Nazionale</h3>
<input type="text" name="searchAziendaField" id="searchAziendaField" class="required" >
						
					
<button>Genera Nuovo Codice Univoco</button>
				

		</div>


		<iframe src="empty.html" name="server_commands" id="server_commands"
			style="visibility: hidden" height="0"></iframe>
</body>
