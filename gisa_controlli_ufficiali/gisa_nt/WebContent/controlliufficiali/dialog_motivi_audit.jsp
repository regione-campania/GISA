<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.vigilanza.base.MotivoAudit"%>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterDialogCu.js"></script>

<%@page import="org.aspcfs.modules.vigilanza.base.MotivoIspezione"%>
<%@page import="java.util.ArrayList"%>

<script>

function settaTipoAudit()
{	
	

	
	$("#containerTipoIsezioneSelected input[name='auditTipo']").each(function(i) {
		$(this).remove();
		
	});
	$("#containerTipoIsezioneSelected input[name='tipoAudit']").each(function(i) {
		$(this).remove();
		
	});
	$("#containerTipoIsezioneSelected input[name='bpi']").each(function(i) {
		$(this).remove();
		
	});
	$("#containerTipoIsezioneSelected input[name='haccp']").each(function(i) {
		$(this).remove();
		
	});
	
	$("table[id='tipo_cu'] tr").not(":first").each(function() {
		$(this).remove();
	});
	
	
	var arrayAuditTipo = new Array();
	var arrayTipoAudit = new Array();
	var arrayBpi = new Array();
	var arrayHaccp = new Array();
	
	var iAT = 0 ;
	var iTA = 0 ;
	var iBP = 0 ;
	var iHA=0;
	
	var i =1 ;
	$("input[name='auditTipoDialog']:checked").each(function(i) {
		
		var valoreSel = $(this).val();
		var audittipo = valoreSel.split(',')[0];
		var tipoaudit = valoreSel.split(',')[1];
		var bpiohaccp = valoreSel.split(',')[2];

		var trovatoAuditTipo = false ;
		var trovatoTipoAudit = false ;
		var trovatoTipoBpi = false ;
		var trovatoTipoHaccp = false ;
		
		$.each( arrayAuditTipo, function(  index,value ) {
			if (value==audittipo){
				trovatoAuditTipo = true ;
				return false;
			}
			else
				{
				trovatoAuditTipo = false ;
				}
			});
		
		$.each( arrayTipoAudit, function(  index,value ) {
			if (value==tipoaudit){
				trovatoTipoAudit = true ;
				return false;
			}
			else
				{
				trovatoTipoAudit = false ;
				}
			});
		
		if (tipoaudit=='2')
			{
			$.each( trovatoTipoBpi, function(  index,value ) {
			if (value==bpiohaccp){
				trovatoTipoBpi = true ;
				return false;
			}
			else
				{
				trovatoTipoBpi = false ;
				}
			});
			}
		else	
		if (tipoaudit=='3')
		{
		$.each( trovatoTipoHaccp, function(  index,value ) {
		if (value==bpiohaccp){
			trovatoTipoHaccp = true ;
			return false;
		}
		else
			{
			trovatoTipoHaccp = false ;
			}
		});
		}
		
		
		
		if (trovatoAuditTipo==false){
		arrayAuditTipo[iAT]=audittipo;
		iAT++ ;
		
		
		
		}
		
		if (trovatoTipoAudit==false){
			arrayTipoAudit[iTA]=tipoaudit;
			iTA++ ;
			
			
			}
		
		if (trovatoTipoBpi==false && tipoaudit=='2'){
			arrayBpi[iBP]=bpiohaccp;
			iBP++ ;
			
			}
		
		if (trovatoTipoHaccp==false && tipoaudit=='3'){
			arrayHaccp[iHA]=bpiohaccp;
			iHA++ ;
			
		
			}
		
		
		
		
		$('<tr name="tipo_audit"><td><b>'+(parseInt(i)+1)+'</b></td><td>'+$(this).attr('descrizione')+'</td></tr>').appendTo("table[id='tipo_cu']");
		
	});
	
	
	
	
	$.each( arrayAuditTipo, function(  index,value ) {
		
		$('<input>').attr({
		    type: 'hidden',
		    id: 'auditTipo',
		    name: 'auditTipo',
		    value :value
		}).appendTo("div[id='containerTipoIsezioneSelected']");
		
	});
$.each( arrayTipoAudit, function(  index,value ) {
	
	$('<input>').attr({
	    type: 'hidden',
	    id: 'tipoAudit',
	    name: 'tipoAudit',
	    value :value
	}).appendTo("div[id='containerTipoIsezioneSelected']");
		
	});
$.each( arrayBpi, function(  index,value ) {
	
	$('<input>').attr({
	    type: 'hidden',
	    id: 'bpi',
	    name: 'bpi',
	    value :value
	}).appendTo("div[id='containerTipoIsezioneSelected']");
	
});
$.each( arrayHaccp, function(  index,value ) {
	
	$('<input>').attr({
	    type: 'hidden',
	    id: 'haccp',
	    name: 'haccp',
	    value :value
	}).appendTo("div[id='containerTipoIsezioneSelected']");
	

	
});
	
mostraMenu4('addticket');

	
	

	
	
	//document.getElementById('containerTipoIsezioneSelected').removeChild(document.getElementById('tipoIspezione'+tipiispezione[i].value));

	
	
}
$(function () {
	    
	 
	 $.fx.speeds._default = 1000;
	
	 $( "#dialogMotiviAudit" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"OGGETTI DELL'AUDIT",
	        width:850,
	        height:500,
	        draggable: false,
	        modal: true,
	        buttons:{
	        	 "FATTO": function() {settaTipoAudit(); $(this).dialog("close");} ,
	        	 "ESCI" : function() { $(this).dialog("close");}
	        	
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
</script>

	<div id ="dialogMotiviAudit">
				
				<%
				ArrayList<MotivoAudit> listaMotiviAudit = (ArrayList<MotivoAudit>) request.getAttribute("ListaMotiviAudit");
				
				%>
				




			
<table  class="tablesorter" id="tablelistaaudit">

	<thead>
		<tr class="tablesorter-headerRow" role="row">
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER MOTIVO ISPEZIONE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">TECNICA DI CONTROLLO</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER MOTIVO ISPEZIONE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">MOTIVO DELL'AUDIT</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER MOTIVO ISPEZIONE" class="first-name filter-select"><div class="tablesorter-header-inner">DESCRIZIONE DELL AUDIT</div></th>
			
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER MOTIVO ISPEZIONE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">TIPO OGGETTO AUDIT</div></th>
			
						<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER MOTIVO ISPEZIONE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">OGGETTO DELL'AUDIT</div></th>
			
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER DESCRIZIONE PIANO" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">&nbsp;</th>
					
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">
	<%
	for (MotivoAudit motivo : listaMotiviAudit)
	{
		%>
		
		<tr>
		<td> <%="AUDIT" %> </td>
		<td> <%=motivo.getDescrizioneAuditTipo() %> </td>
		<td> <%=toHtml2(motivo.getDescrizioneTipoAudit()) %> </td>
		<td> <%=toHtml2(motivo.getTipo_bpi_haccp()) %> </td>
		<td> <%=toHtml2(motivo.getDescrizioneBpi_o_haccp()) %> </td>
		<td> <input type = "checkbox"  descrizione = "<%=motivo.getDescrizioneAuditTipo() + ((motivo.getDescrizioneTipoAudit()!=null && !"".equalsIgnoreCase(motivo.getDescrizioneTipoAudit())) ? toHtml2("->"+motivo.getDescrizioneTipoAudit()) : "")+ ((motivo.getDescrizioneBpi_o_haccp() !=null && !"".equalsIgnoreCase(motivo.getDescrizioneBpi_o_haccp()))?toHtml2("->"+motivo.getDescrizioneBpi_o_haccp()):"") %>"  name = "auditTipoDialog" value = "<%=motivo.getIdAuditTipo()+","+motivo.getIdTipoAudit()+","+motivo.getIdBpi_o_haccp()%>"> 
		
		</td>
		</tr>
		
		<%
		
	}
	 
	%>
	
	</tbody>
	
	</table>
	
		</div>
		
		
		