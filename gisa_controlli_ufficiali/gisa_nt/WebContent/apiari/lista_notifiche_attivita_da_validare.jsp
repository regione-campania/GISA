<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="ext.aspcfs.modules.apicolture.actions.StabilimentoAction"%>
<%@page import="java.util.Iterator"%>
<%@page import="ext.aspcfs.modules.apiari.base.Operatore"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,ext.aspcfs.modules.apiari.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="ListaAttivitaDaValidare"
	class="ext.aspcfs.modules.apiari.base.OperatoreList" scope="request" />

<jsp:useBean id="MovimentazioniList"
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

<link rel="stylesheet" type="text/css"
	href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css"
	href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>
<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>
<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/tableJqueryFilterApi.js"></script>
<script src="javascript/jquery.searchable-1.0.0.min.js"></script>


  <link rel="stylesheet" type="text/css" media="screen" href="css/jquery.ui.combogrid.css"/>
  <script type="text/javascript" src="javascript/jquery.ui.combogrid.js"></script>



<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/gestoreCodiceFiscale.js"></script>
<SCRIPT src="javascript/apiari_search_aziende.js"></SCRIPT>
<script type="text/javascript" src="dwr/interface/ApicolturaRemote.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<style>
<!--

.combogrid{
	/*max-width: 800px;
	min-width: 500px;*/
	font-size: 0.8em !important;
	width: 25%;
	height: 50%;
	overflow: scroll;
	
	
}
.cg-colHeader-label {
	padding: 10;
	margin:10;
	float:center;
}
.cg-DivItem {
	
	float:center;
	font-size: 0.8em;
	overflow: visible;
	height: auto;
}
-->
</style>
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
		document.getElementById('body'+tipoPraticaIN).remove(document.getElementById(tipoPraticaIN+idApiarioIN))
		}
}




function mostraCodici(idOperatore,value,codice)
{
	
	if (value=='1')
		{
		$( "#searchAziendaField" ).val("");
		$( "#searchAziendaField2" ).val("");
		if(codice.length==8)
			{
		$.ajax({
			  // definisco il tipo della chiamata
			  type: "POST",
			  // specifico la URL della risorsa da contattare
			  url: "ApicolturaAttivita.do?command=GetCodiceAziendaEsistente&idOperatore="+idOperatore+"&codice="+codice,
			  // passo dei dati alla risorsa remota
			 
			  // definisco il formato della risposta
			  dataType: "json",
			  // imposto un'azione per il caso di successo
			  success: function(json){
				  if (json != null)
					  {
					  if (json.rows.length>0)
						  {
					  alert("CODICE AZIENDA DISPONIBILE"+json.rows[0].codiceAzienda);
			    	$("#searchAziendaField").val(json.rows[0].codiceAzienda);
				  $("#searchAziendaField1").val(json.rows[0].codiceAzienda);
						  }
					  else
						  {
						  alert("CODICE AZIENDA NON DISPONIBILE ");
						  $("#searchAziendaField").val("");
						  
						  }
					  }
				  else
					  {
					  alert("CODICE AZIENDA NON DISPONIBILE ");
					  $("#searchAziendaField").val("");
					  
					  
					  }
			  },
			  // ed una per il caso di fallimento
			  error: function(){
			    alert("AZIENDA NON ESISTENTE PER IL CODICE FISCALE DEL PROPRIETARIO");
			  }
			});
			}else
				{
				alert("Attenzione! inserire il codice azienda di 8 caratteri");
			
				  $("#searchAziendaField").val("");
				  
				}
				}
	else
		{
		
		$( "#searchAziendaField1" ).val("");

		  $("#searchAziendaField").val("");
		  
		
		}
	
}

$(function () {
	
	
	
	
	 $( "#searchAzienda" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"ASSEGNA CODICE AZIENDALE",
	        width:850,
	        height:400,
	        draggable: false,
	        modal: true,
	        position: 'top',
	        open:function(){$("#searchAziendaFieldinput").val("");},
	        buttons:{
	        	 "Salva": function() {if ($( "#searchAziendaField" ).val()!=""){
	        		 codice = $( "#searchAziendaField" ).val();
	        		 sigla = codice.substring(3,5);
	        		 
	        		 if (sigla.toLowerCase() =='na' || sigla.toLowerCase() =='bn' || sigla.toLowerCase() =='ce' || sigla.toLowerCase() =='sa' || sigla.toLowerCase() =='av')
		        		document.forms['generanumero'].submit() ;
	        			 
		        		else 
		        			alert("Attenzione! non e possibile validare una impresa fuori Regione");	 
	        	 }else {alert("Selezionare un Codice Azienda");}} ,
	        		
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

function generaCodice(comune,idOperatore)
{
	//$( '#searchAzienda' ).dialog('open');
	if(confirm('Sicuro di voler generare il Codice Osa per questa impresa?'))
		location.href="ApicolturaAttivita.do?command=AssegnaNumero&opId="+idOperatore;

}

function generaCodiceNazionale(flagincompleto,comuneIstat,siglaProvincia,idOperatore)
{
	  
	if (flagincompleto==true)
		{
		alert("Attenzione! Non è possibile validare la scheda in mancano informazioni. Contattare l'apicoltore per il completamento della scheda.");
		
		}
	else
		{
		
		$( "#searchAziendaField1" ).val(comuneIstat+siglaProvincia);
	document.getElementById("idOperatore").value=idOperatore;
	document.getElementById("opId").value=idOperatore;
	$( "#searchAziendaField2" ).combogrid({
		  debug:true,
		  pageSize: 10,
		  colModel: [{'columnName':'codiceAzienda','width':'20','label':'Codice'}],
		  url: 'ApicolturaAttivita.do?command=GetCodiceAziendaDisponibile&idOperatore='+idOperatore,
		  select: function( event, ui ) {
			  $( "#searchAziendaField2" ).val( ui.item.codiceAzienda );
			 
			  $("#searchAziendaField").val( ui.item.codiceAzienda );

			 
			  return false;
		  }		  
		 
	});

	 $( "#searchAzienda" ).dialog('open');
		}

}

</script>



<body onload="resizeGlobalItemsPane('hide')">


	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td>Attivita Da Validare</td>
		</tr>
	</table>

	<fieldset>
		<legend>LISTA NUOVE ATTIVITA</legend>

<div class="pager">
	Page: <select class="gotoPage"></select>		
	<img src="javascript/img/first.png" class="first" alt="First" title="First page" />
	<img src="javascript/img/prev.png" class="prev" alt="Prev" title="Previous page" />
	<img src="javascript/img//next.png" class="next" alt="Next" title="Next page" />
	<img src="javascript/img/last.png" class="last" alt="Last" title= "Last page" />
	<select class="pagesize">
		<option value="10">10</option>
		<option value="20">20</option>
		<option value="30">30</option>
		<option value="40">40</option>
	</select>
</div>
		<table class="tablesorter">

			<thead>
				<tr class="tablesorter-headerRow" role="row">

					<th
						aria-label="Ragione Sociale: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						data-placeholder="FILTRO PER RAGIONE SOCIALE"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">RAGIONE SOCIALE</div></th>

					<th
						aria-label="Proprietario ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER PROPRIETARIO"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">PROPRIETARIO</th>
					<th
						aria-label="CF Proprietario ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER CF PROPRIETARIO"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">CODICE FISCALE
							PROPRIETARIO</th>
					<th
						aria-label="Comune ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER COMUNE"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">COMUNE</th>
					<th
						aria-label="Indirizzo ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER INDIRIZZO"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">INDIRIZZO</th>
					<th
						aria-label="Tipo attivita ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER ATTIVITA"
						class="first-name filter-select"><div
							class="tablesorter-header-inner">TIPO ATTIVITA</th>
					
					<th
						aria-label="Codice Azienda: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						class="filter-false tablesorter-header"><div
							class="tablesorter-header-inner">ASL</div></th>
					<th
						aria-label="Codice Azienda: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						class="filter-false tablesorter-header"><div
							class="tablesorter-header-inner">OPERAZIONI</div></th>


				</tr>
			</thead>
			<tbody aria-relevant="all" aria-live="polite">

				<%
					if (ListaAttivitaDaValidare.size() > 0) {
						Iterator<Operatore> itOperatori = ListaAttivitaDaValidare.iterator();
						while (itOperatori.hasNext()) {
							Operatore thisOperatore = itOperatori.next();
							boolean flagIncompleto= (thisOperatore.getStato()==StabilimentoAction.API_STATO_PREGRESSO_DA_VALIDARE);
				%>
				<tr>
					<td><%=toHtml2(thisOperatore.getRagioneSociale())%></td>
					<td><%=toHtml2(thisOperatore.getRappLegale().getCognome() + " "
							+ thisOperatore.getRappLegale().getNome())%></td>
					<td><%=toHtml2(thisOperatore.getRappLegale().getCodFiscale())%></td>
					<td><%=toHtml2(thisOperatore.getSedeLegale().getDescrizioneComune())%></td>
					<td><%=toHtml2(thisOperatore.getSedeLegale().getVia())%></td>
					<td><%=toHtml2(TipoAttivitaApi.getSelectedValue(thisOperatore.getIdTipoAttivita()))%></td>
					<td><%=toHtml2(SiteIdList.getSelectedValue(thisOperatore.getIdAsl()))%></td>


					<td width="10%">
						
						<button
							onclick="generaCodiceNazionale( <%=flagIncompleto %>,'<%=thisOperatore.getSedeLegale().getCodiceIstatComune()%>','<%=thisOperatore.getSedeLegale().getSiglaProvincia()%>',<%=thisOperatore.getIdOperatore()%>)">Valida</button>
				<a href="#" onclick="javscript:window.open('ApicolturaAttivita.do?command=Details&popup=true&stabId=<%=thisOperatore.getIdOperatore()%>&opId=<%=thisOperatore.getIdOperatore()%>')">Apri Modello A</a>

					</td>

				</tr>
				<%
					}
					} else {
				%>
				<tr>
					<td colspan="9">NESSUNA NUOVA RICHIESTA DI ATTIVITA NEL
						PROPRIO TERRITORIO</td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
<div class="pager">
	Page: <select class="gotoPage"></select>		
	<img src="javascript/img/first.png" class="first" alt="First" title="First page" />
	<img src="javascript/img/prev.png" class="prev" alt="Prev" title="Previous page" />
	<img src="javascript/img//next.png" class="next" alt="Next" title="Next page" />
	<img src="javascript/img/last.png" class="last" alt="Last" title= "Last page" />
	<select class="pagesize">
		<option value="10">10</option>
		<option value="20">20</option>
		<option value="30">30</option>
		<option value="40">40</option>
	</select>
</div>
	</fieldset>

	<br>
	<br>



	<div id="searchAzienda">
		<div id="messaggioErrore"></div>

		<form method="post" action="ApicolturaAttivita.do?command=AssegnaNumero" name="generanumero">

			 <fieldset>
			<legend>HO GIA UN CODICE</legend>
			<table style="width: 100%">
			<tr><td width="50%"><input type ="button" value="VERIFICA DISPONIBILITA IN BDN" onclick="mostraCodici(document.getElementById('idOperatore').value,'1',document.getElementById('searchAziendaField1').value)"></td>
			<td width="50%">	<input type="text" value="" pattern="([0-9]{3})(BN|NA|CE|SA|AV|bn|na|ce|sa|av|Bn|Na|Ce|Sa|Av)(([0-9])([0-9]|[A-Fa-f])([0-9]))" 
			  onfocusout="if(!validity.valid){this.value=this.value.substring(0,5);alert('Inserire un codice azienda che rispetti il pattern');}" name="searchAziendaField1" id="searchAziendaField1"   size="8" maxlength="8"   placeholder="">  </td>
			 
			</tr>
			</table>
			</fieldset>
			
			<hr align="left" size="1" width="100%"  noshade>
			
			<fieldset>
			<legend>DAMMI UN NUOVO CODICE</legend>
			<table style="width: 100%">
			<tr>
			<td width="50%" id ="selectcodicelabel">SELEZIONA CODICE DA LISTA</td>
			<td width="50%">
			 
			<input type="text" name="searchAziendaField2" id="searchAziendaField2"  size="60  class="required" readonly="readonly" placeholder="CLICCA PER RICERCARE CODICE DISPONIBILE">  </td>
			</tr>
			</table>
			</fieldset>
			
						 <input type="text" name="searchAziendaField" id ="searchAziendaField" value=""   onfocusout="if(!validity.valid){this.value=this.value.substring(0,5);alert('Inserire un codice azienda che rispetti il pattern');}"
						 pattern="([0-9]{3})(BN|NA|CE|SA|AV|bn|na|ce|sa|av|Bn|Na|Ce|Sa|Av)(([0-9])([0-9]|[A-Fa-f])([0-9]))" title="Formato codice azienda non valido" maxlength="8">
			
			 <input type="hidden" id="idOperatore" value="">
			  <input type="hidden" name="opId" id="opId" value="">
				
			<div id="loading"></div>	
		</form>

	</div>


	<iframe src="empty.html" name="server_commands" id="server_commands"
		style="visibility: hidden" height="0"></iframe>
</body>
