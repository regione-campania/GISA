<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@page import="it.us.web.bean.validazione.Richiesta"%>

<jsp:useBean id="listaRichieste" class="java.util.ArrayList" scope="request"/>

<%@ include file="../guc/modalWindow.jsp"%>
<%@ include file="../guc/initPage.jsp"%>

<link rel="stylesheet" type="text/css" href="js/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="js/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="js/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="js/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="js/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="js/jquerypluginTableSorter/tableJqueryFilterDialogEpc.js"></script>

<script>

function vediRichiesta(num){
	loadModalWindow();
	document.getElementById("numeroRichiesta").value = num;
	document.getElementById("formRichieste").submit();
} 

function rifiutaRichiesta(){
	
	$("#dialogRifiuta").dialog("close");
	document.getElementById("formRichieste").action="validazione.Validazione.us";
	document.getElementById("formRichieste").submit();
} 


function processaRichiesta(){
	
	$("#dialogProcessa").dialog("close");
	document.getElementById("formRichieste").action="validazione.Validazione.us";
	document.getElementById("formRichieste").submit();
		
} 

function mostraProcessa(numeroRichiesta, idTipoOperazione, idTipologiaUtente, hasRuoloGisa){ 
	loadModalWindow();
	$("#numeroRichiesta").val(numeroRichiesta);
	$("#idTipoOperazione").val(idTipoOperazione);
	
	if (idTipoOperazione != 3 && hasRuoloGisa=="true") {
		
		if (idTipologiaUtente == 1)
			$("#inDpatTemp_Label").show();
		else
			$("#inDpatTemp_Label").hide();
		
		if (idTipologiaUtente == 1 || idTipologiaUtente == 2 || idTipologiaUtente == 15 || idTipologiaUtente == 19){
			$("#inNucleoTemp_Label").show();
			if (idTipologiaUtente == 15){ // CENTRI DI RIFERIMENTO: HANNO SEMPRE NUCLEO ISPETTIVO = TRUE
				$("#inNucleoTemp").prop('checked', true); 
				$("#inNucleo").prop('checked', true); 
				$("#inNucleoTemp_Label").hide();
			}
			}
		else
			$("#inNucleoTemp_Label").hide();
	}
	else{
		$("#inDpatTemp_Label").hide();
		$("#inNucleoTemp_Label").hide();
	}
	
	$("#dialogProcessa").dialog();
}
function nascondiProcessa(){
	loadModalWindowUnlock();
	$("#numeroRichiesta").val("");
	$("#idTipoOperazione").val("");
	$("#inDpat").prop('checked', false); 
	$("#inNucleoTemp").prop('checked', false); 
	$("#inDpatTemp").prop('checked', false); 
	$("#inNucleo").prop('checked', false); 
	$("#dialogProcessa").dialog("close");
}

function mostraRifiuta(numeroRichiesta, idTipoOperazione){ 
	loadModalWindow();
	$("#numeroRichiesta").val(numeroRichiesta);
	$("#idTipoOperazione").val(idTipoOperazione);
	
	$("#dialogRifiuta").dialog();
}
function nascondiRifiuta(){
	loadModalWindowUnlock();
	$("#numeroRichiesta").val("");
	$("#idTipoOperazione").val("");
	$("#dialogRifiuta").dialog("close");
}

</script>

 <%! public static String fixData(String timestring)
  {
	  String toRet = "";
	  if (timestring == null)
		  return toRet;
	  String anno = timestring.substring(0,4);
	  String mese = timestring.substring(5,7);
	  String giorno = timestring.substring(8,10);
	  String ora = timestring.substring(11,13);
	  String minuto = timestring.substring(14,16);
	  String secondi = timestring.substring(17,19);
	  toRet =giorno+"/"+mese+"/"+anno+" "+ora+":"+minuto+":"+secondi;
	  return toRet;
	  
  }%>
   
  
  <% String host = "wwwcol.gisacampania.it"; %>
   <script>
  if (window.location.href.indexOf("guc.") > -1){  <% host = "www.gisacampania.it"; %> }
  </script>

<div align="center">
		<a href="Index.us" onClick="loadModalWindow();" style="margin: 0px 0px 0px 50px; font-size: 20px">Indietro</a>
	</div>
<br/><br/>

<div align="right">
		<a href="jsp/validazione/Manuale-ProcessaRichiesta.pdf" style="margin: 0px 0px 0px 50px; font-size: 20px" target="_blank">Manuale <img src="images/adobe-icon.png" width="50px"/></a>
	</div>
<br/><br/>


<form method="post" action = "validazione.ToRichiesta.us" id="formRichieste" name="formRichieste">

<table  class="tablesorter" id = "tablelistaendpoint" style="width:100%">

<thead>
		<tr class="tablesorter-headerRow" role="row">
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="NUMERO" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Numero Richiesta</div></th>
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="TIPO" class="first-name filter-select"><div class="tablesorter-header-inner">Tipo richiesta</div></th>
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="DATA" class="sorter-shortDate dateFormat-ddmmyyyy"><div class="tablesorter-header-inner">Data richiesta</div></th>
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="CF" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Codice fiscale</div></th>
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="TUTTI" class="first-name filter-select"><div class="tablesorter-header-inner">Ruolo GISA</div></th>
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="TUTTI" class="first-name filter-select"><div class="tablesorter-header-inner">Ruolo BDR</div></th>
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="TUTTI" class="first-name filter-select"><div class="tablesorter-header-inner">Ruolo VAM</div></th>
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="TUTTI" class="first-name filter-select"><div class="tablesorter-header-inner">Ruolo DIGEMON</div></th>
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="STATO" class="first-name filter-select"><div class="tablesorter-header-inner">Stato</div></th>
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO" class="filter-false tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Operazioni</div></th>
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">


<% for (int i = 0; i< listaRichieste.size(); i++) {  
Richiesta ric = (Richiesta) listaRichieste.get(i);
%>
<tr style="border:1px solid black;">
<td style="border:1px solid black;">
<%-- <a href="http://<%=host %>/moduloSpid/db.php?operation=printPdf&numero_richiesta=<%=ric.getNumeroRichiesta() %>" target="_blank"> --%>
<%=ric.getNumeroRichiesta() %>
<!-- </a> -->
</td>
<td style="border:1px solid black;"><%=toHtml(ric.getTipoRichiesta()) %></td>
<td style="border:1px solid black;"><%=fixData(ric.getDataRichiesta().toString()) %></td>
<td style="border:1px solid black;"><%=toHtml(ric.getCodiceFiscale())%></td>
<td style="border:1px solid black;"><%=ric.getIdRuoloGisa()>0 ? toHtml(ric.getRuoloGisa()) :  ric.getIdRuoloGisaExt()>0 ? toHtml(ric.getRuoloGisaExt()) : "" %></td>
<td style="border:1px solid black;"><%=ric.getIdRuoloBdu() > 0 ? toHtml(ric.getRuoloBdu()) : "" %></td>
<td style="border:1px solid black;"><%=ric.getIdRuoloVam() > 0 ? toHtml(ric.getRuoloVam()) : "" %></td>
<td style="border:1px solid black;"><%=ric.getIdRuoloDigemon() > 0 ? toHtml(ric.getRuoloDigemon()) : "" %></td>
<td style="border:1px solid black;"><%= ric.getStato() == Richiesta.STATO_DA_VALIDARE ? "DA VALIDARE" : ric.getStato() == Richiesta.STATO_COMPLETATA ? "COMPLETATA" : ric.getStato() == Richiesta.STATO_RIFIUTATA ? "RIFIUTATA" : ""%></td>
<td style="border:1px solid black;">

<input type="button" value="VEDI" onClick="vediRichiesta('<%=ric.getNumeroRichiesta()%>')"/>

<% if (ric.getStato() == Richiesta.STATO_DA_VALIDARE){ %>
<input type="button" style="background: red" value="RIFIUTA" onClick="mostraRifiuta('<%=ric.getNumeroRichiesta()%>', '999')"/>
<input type="button" onClick="mostraProcessa('<%=ric.getNumeroRichiesta()%>', '<%=ric.getIdTipoRichiesta() %>', '<%=ric.getIdTipologiaUtente() %>', '<%=(ric.getIdRuoloGisa()>0 || ric.getIdRuoloGisaExt()>0) ? "true" : "false" %>');" value="PROCESSA"/>

<%} %>

</td>
</tr>

<%} %>

</tbody>
		<input type="text" id="numeroRichiesta" name="numeroRichiesta" value="" style="display:none"/>
		<input type="text" id="idTipoOperazione" name="idTipoOperazione" value="" style="display:none"/>
		<input type="checkbox" id="inDpat" name="inDpat" value="true" style="display:none"/>
		<input type="checkbox" id="inNucleo" name="inNucleo" value="true" style="display:none"/>
	
	</table>
</form>	



<script>

</script>

<div id="dialogProcessa" style="display: none;" title="Registrazione Ecosistema GISA">
<div align="center">Processare questa richiesta?

<label id="inNucleoTemp_Label"><br/><br/> Il nominativo può essere selezionato nel Nucleo Ispettivo? <input type="checkbox" id="inNucleoTemp" name="inNucleoTemp" value="true" onClick="document.getElementById('inNucleo').checked = this.checked"/></label>
<label id="inDpatTemp_Label"><br/><br/>Il nominativo può appartenere ad un organigramma? <input type="checkbox" id="inDpatTemp" name="inDpatTemp" value="true" onClick="document.getElementById('inDpat').checked = this.checked"/></label>

<br/><br/>

<input type="button" id="buttonAnnulla" value="ANNULLA" style="width:100px; height: 50px; font-size: 15px" onClick="nascondiProcessa()"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" id="buttonProcessa" value="PROCESSA" style="width:100px; height: 50px; font-size: 15px" onClick="processaRichiesta()"/>
</div>
</div>

<div id="dialogRifiuta" style="display: none;" title="Registrazione Ecosistema GISA">
<div align="center">Rifiutare questa richiesta?

<input type="button" id="buttonAnnulla" value="ANNULLA" style="width:100px; height: 50px; font-size: 15px" onClick="nascondiRifiuta()"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" id="buttonRifiuta" value="RIFIUTA" style="width:100px; height: 50px; font-size: 15px" onClick="rifiutaRichiesta()"/>
</div>
</div>







