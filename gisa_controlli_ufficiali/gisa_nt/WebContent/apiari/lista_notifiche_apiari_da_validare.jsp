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
	class="ext.aspcfs.modules.apiari.base.StabilimentoList" scope="request" />

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

<link rel="stylesheet" type="text/css"
	href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css"
	href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>
<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>

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

var campoLat;
var campoLong;
	
function showCoordinate(address,city,prov,cap,campo_lat,campo_long)
{
campoLat = campo_lat;
campoLong = campo_long;
if (city=='' || prov==''){alert('Attenzione! riemprire i campi Comune e Provincia!');}
else{
Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
}
}

function setGeocodedLatLonCoordinate(value){
	campoLat.value = value[0];
	campoLong.value =value[1];
}   


function mostraCodici(idOperatore,value)
{
	
	if (value=='1')
		{
		$( "#searchAziendaField" ).val("");
		$( "#searchAziendaField2" ).val("");
		
		$.ajax({
			  // definisco il tipo della chiamata
			  type: "POST",
			  // specifico la URL della risorsa da contattare
			  url: "ApicolturaAttivita.do?command=GetCodiceAziendaEsistente&idOperatore="+idOperatore,
			  // passo dei dati alla risorsa remota
			 
			  // definisco il formato della risposta
			  dataType: "json",
			  // imposto un'azione per il caso di successo
			  success: function(json){
				  if (json != null)
					  {
					  alert(json.rows[0].codiceAzienda);
			    	$("#searchAziendaField").val(json.rows[0].codiceAzienda);
				  $("#searchAziendaField1").val(json.rows[0].codiceAzienda);
					  }
				  else
					  alert("AZIENDA NON ESISTENTE PER IL CODICE FISCALE DEL PROPRIETARIO");
			  },
			  // ed una per il caso di fallimento
			  error: function(){
			    alert("AZIENDA NON ESISTENTE PER IL CODICE FISCALE DEL PROPRIETARIO");
			  }
			});
				}
	else
		{
		
		$( "#searchAziendaField1" ).val("");
		
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
	        	 "Salva": function() {if ($( "#searchAziendaField" ).val()!=""){document.forms['generanumero'].submit() ;}else {alert("Selezionare un Codice Azienda");}} ,
	        	
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
			<td>SINCRONIZZAZIONE APIARI</td>
		</tr>
	</table>



	<fieldset>
		<legend>LISTA APIARI NON SINCRONIZZATI CON CODICE AZIENDA PRESENTE</legend>




		<table class="tablesorter">

			<thead>
				<tr class="tablesorter-headerRow" role="row">

<th
						aria-label="Ragione Sociale: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						data-placeholder="FILTRO PER CODICE AZIENDA"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">CODICE AZIENDA</div></th>
							
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
							class="tablesorter-header-inner">UBICAZIONE APIARIO</div></th>
							
							<th
						aria-label="Codice Azienda: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						class="filter-false tablesorter-header"><div
							class="tablesorter-header-inner">PROGRESSIVO</div></th>
							
							<th
						aria-label="Codice Azienda: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						class="filter-false tablesorter-header"><div
							class="tablesorter-header-inner">MOTIVO KO</div></th>
				


				</tr>
			</thead>
			<tbody aria-relevant="all" aria-live="polite">

				<%
					if (ListaAttivitaDaValidare.size() > 0) {
						Iterator<Stabilimento> itOperatori = ListaAttivitaDaValidare.iterator();
						while (itOperatori.hasNext()) {
							Stabilimento thisOperatore = itOperatori.next();
							boolean flagIncompleto= (thisOperatore.getOperatore().getStato()==StabilimentoAction.API_STATO_PREGRESSO_DA_VALIDARE);
				%>
				<tr>
				<td><%=toHtml2(thisOperatore.getOperatore().getCodiceAzienda())%></td>
					<td><%=toHtml2(thisOperatore.getOperatore().getRagioneSociale())%></td>
					<td><%=toHtml2(thisOperatore.getOperatore().getRappLegale().getCognome() + " "
							+ thisOperatore.getOperatore().getRappLegale().getNome())%></td>
					<td><%=toHtml2(thisOperatore.getOperatore().getRappLegale().getCodFiscale())%></td>
					<td><%=toHtml2(thisOperatore.getOperatore().getSedeLegale().getDescrizioneComune())%></td>
					<td><%=toHtml2(thisOperatore.getOperatore().getSedeLegale().getVia())%></td>
					<td><%=toHtml2(TipoAttivitaApi.getSelectedValue(thisOperatore.getOperatore().getIdTipoAttivita()))%></td>
					<td><%=toHtml2(SiteIdList.getSelectedValue(thisOperatore.getOperatore().getIdAsl()))%></td>
					
					<td><%=toHtml2(thisOperatore.getSedeOperativa().getDescrizioneComune()) + " " +toHtml2(thisOperatore.getSedeOperativa().getDescrizione_provincia())%></td>
					<td><%=thisOperatore.getProgressivoBDA()%></td>
					<td><%=toHtml(thisOperatore.getErroreValidazione())%>
					<%if (thisOperatore.getErroreValidazione()!= null && (thisOperatore.getErroreValidazione().toLowerCase().contains("longitudine") || thisOperatore.getErroreValidazione().toLowerCase().contains("coordinate") || thisOperatore.getErroreValidazione().toLowerCase().contains("latitudine")))
					{%>
					<form method="post" action="OpuSuapStab.do?command=ModificaCoordinateApiario&auto-populate=true">
						<input type = "text" style="background-color: red" name="latitudine" id="lat<%=thisOperatore.getIdStabilimento() %>" value="<%=thisOperatore.getSedeOperativa().getLatitudine() %>"/>
						<input type = "text" style="background-color: red" name="longitudine" id="lon<%=thisOperatore.getIdStabilimento() %>" value="<%=thisOperatore.getSedeOperativa().getLongitudine() %>"/>
						<input type = "hidden" name="idStabilimento" value="<%=thisOperatore.getIdStabilimento() %>"/>
						<input type ="button" value = "Ricalcola Coordinate" onclick="showCoordinate('','<%=toHtml2(thisOperatore.getSedeOperativa().getDescrizioneComune())%>','<%=thisOperatore.getSedeOperativa().getDescrizione_provincia() %>','<%=thisOperatore.getSedeOperativa().getCap() %>',document.getElementById('lon<%=thisOperatore.getIdStabilimento()%>'),document.getElementById('lat<%=thisOperatore.getIdStabilimento()%>'))"/>
						<input type ="button" value = "Aggiorna Coordinate" onclick="aggiornaCoordinate(<%=thisOperatore.getIdStabilimento()%>,document.getElementById('lat<%=thisOperatore.getIdStabilimento() %>').value,document.getElementById('lon<%=thisOperatore.getIdStabilimento() %>').value )"/>
					
					</form>
					
					
					<%} %>
					
					</td>


					

				</tr>
				<%
					}
					} else {
				%>
				<tr>
					<td colspan="10">NON CI SONO APIARI DA SINCRONIZZARE VERSO LA BDN</td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<%
		if (ListaAttivitaDaValidare.size()>0)
		{
		%>
		<center>
		<input type="button" class="redBigButton" value = "SINCRONIZZA CON BDN" onclick="javascript:if(confirm('Vuoi Sincronizzare la Lista apiari con la bdn?')==true ){location.href='ApicolturaApiari.do?command=SincronizzaApiari';}">
</center>
<%} %>
	</fieldset>

	<br>
	<br>
<script>






function aggiornaCoordinate(idStab,latitudine,longitudine)
{
	
	ApicolturaRemote.aggiornaCoordinateApiario(idStab,latitudine,longitudine,aggiornaCoordinateCallback);
}


function aggiornaCoordinateCallback(value)
{
	if (value==true)
		{
		alert("Coordinate Aggiornate Correttamente");
		 campoLat.style="background-color: green";
		   campoLong.style="background-color: green";
		}
}



	
</script>


	


	<iframe src="empty.html" name="server_commands" id="server_commands"
		style="visibility: hidden" height="0"></iframe>
</body>
