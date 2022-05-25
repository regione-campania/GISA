<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcf.modules.controlliufficiali.base.Piano"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.modules.vigilanza.base.Ticket"%>

<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterDialogCu.js"></script>




<%@page import="org.aspcfs.modules.vigilanza.base.MotivoIspezione"%>
<%@page import="java.util.ArrayList"%>

<script>

var campoIspCheckGlobal;
var codiceIspezioneGlobal;
var idPianoGlobal;

function settaPiano(campoIspCheck,codiceIspezione,idPiano)
{ 
	 campoIspCheckGlobal = campoIspCheck;
	 codiceIspezioneGlobal = codiceIspezione;
	 idPianoGlobal = idPiano;
	
	var tipoOperatore = document.getElementById("tipoOperatore").value;
	var checkedPianiAttivita = document.querySelectorAll('input[name=tipoIspezioneDialog]:checked');
	var listaPianiAttivitaSelezionati = "";
	for (var i=0; i<checkedPianiAttivita.length; i++){
		var idPianoAttivita = checkedPianiAttivita[i].value;
		listaPianiAttivitaSelezionati= listaPianiAttivitaSelezionati+idPianoAttivita+";;"
	}
	
	PopolaCombo.getCompatibilitaMotiviCU(tipoOperatore, campoIspCheck.value,listaPianiAttivitaSelezionati,{callback:settaPianoCallBack, async:false});
	
}
function settaPianoCallBack(val)
{
	if (val!=null && val!=""){
		alert(val);
		campoIspCheckGlobal.checked=false; 
		
		 campoIspCheckGlobal = null;
		 codiceIspezioneGlobal = null;
		 idPianoGlobal = null;
		 
		return false;
	}
	
	if (codiceIspezioneGlobal=='2a')
	{
	var checkPiano = document.getElementById('piano'+idPianoGlobal);
	if (campoIspCheckGlobal.checked==true)
	{
		checkPiano.checked=true ;
	}
	else
	{
		checkPiano.checked=false ;
	}
		
	}
	
	 campoIspCheckGlobal = null;
	 codiceIspezioneGlobal = null;
	 idPianoGlobal = null;
	 
	 
	}





$(function () {
	    
	 
	 $.fx.speeds._default = 1000;
	
	 $( "#dialogMotiviIspezione" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"MOTIVI DI ISPEZIONE : ATTENZIONE PER TALE LISTA ESISTONO PIU PAGINE. AUMENTARE IL NUMERO DI ELEMENTI O SCORRERE LE PAGINE.UTILIZZARE I FILTRI PER ESEGUIRE RICERCHE.",
	        width:850,
	        height:500,
	        draggable: false,
	        modal: true,
	        buttons:{
	        	 "FATTO": function() {mostraMenuTipoIspezione(document.forms[0].name);$(this).dialog("close"); } ,
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

	<div id ="dialogMotiviIspezione">
				
				<%
				ArrayList<MotivoIspezione> listaMotivi = (ArrayList<MotivoIspezione>) request.getAttribute("ListaMotiviIspezione");
				
				%>
				
				
				<!-- commento al 214 -->
			
<font color="red">ATTENZIONE. Le attività AO9_B, AO9_C e B52 vanno inserite da macroarea iuv e sono valide solo per cani padronali e canili</font>



			
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
<table  class="tablesorter" id = "tablelistaispezioni">

	<thead>
		<tr class="tablesorter-headerRow" role="row">
			<!-- <th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER MOTIVO ISPEZIONE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">TECNICA DI CONTROLLO</div></th> -->
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER TIPO MOTIVO" class="first-name filter-select"><div class="tablesorter-header-inner">TIPO DI MOTIVO</div></th>
			
			<!-- <th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER MOTIVO ISPEZIONE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">MOTIVO DI ISPEZIONE</div></th> -->
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER DESCRIZIONE PIANO MONITORAGGIO/ATTIVITA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">DESCRIZIONE PIANO DI MONITORAGGIO/ATTIVITA</th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER DESCRIZIONE PIANO" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">&nbsp;</th>
					
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">
	<%
	ArrayList<String> listaCodiciInterniPiano = new ArrayList<String>();
	ArrayList<Integer> listaIdAttivita = new ArrayList<Integer>();
	ArrayList<Integer> listaIdPiano = new ArrayList<Integer>();

	for (Piano piano : TicketDetails.getPianoMonitoraggio()){
		listaCodiciInterniPiano.add(piano.getCodice_interno());
		listaIdPiano.add(piano.getId());
	}
// 	for (Map.Entry<Integer, String> entry : TicketDetails.getTipoIspezione().entrySet()) {
// 	    Integer key = entry.getKey();
// 	    String value = entry.getValue();
// 	    if (key!=89)
// 	    	listaIdAttivita.add(key);
// 	}
	
	for (MotivoIspezione motivo : listaMotivi)
	{
		%>
		
		<tr>
		<!-- <td> <%="ISPEZIONE SEMPLICE" %> </td> -->
		<td> <%=toHtml2(motivo.getDescrizioneTecnicaControllo()) %> </td>
		<!-- <td>	<%if(motivo.getDescrizioneMotivoIspezione().equalsIgnoreCase("piano di monitoraggio")){ %> 
					<%=toHtml2(motivo.getDescrizioneMotivoIspezione()) %>
				<%} else {%>
					ATTIVITA
				<%} %>
		</td> -->
		<td> 
			<%if(motivo.getDescrizioneMotivoIspezione().equalsIgnoreCase("piano di monitoraggio")){ %> 
					<%=toHtml2(motivo.getDescrizionePiano()) %>
				<%} else {%>
					<%=motivo.getDescrizioneMotivoIspezione() %>
				<%} %>
		</td>
		<td>  
		
		<input type = "checkbox"  <%= listaIdPiano.contains(motivo.getIdPiano()) || (motivo.getIdPiano()<=0 && listaIdAttivita.contains(motivo.getIdMotivoIspezione())) ? "checked=\"checked\"" : "" %>    codiceinterno="<%=motivo.getCodiceInternoMotivoIspezione() %>" id = "tipoIspezione<%=motivo.getIdMotivoIspezione()+"_"+motivo.getIdPiano() %>"   name = "tipoIspezioneDialog" value = "<%=motivo.getIdMotivoIspezione()+";"+motivo.getIdPiano()%>" onclick="settaPiano(this,'<%=motivo.getCodiceInternoMotivoIspezione()%>',<%=motivo.getIdPiano()%>)">
				 
		 <!-- DESCRIZIONE PIANO FUNZIONA MA PERCHE'???-->
		<input style="visibility: hidden" codiceinterno = "<%=motivo.getCodiceInternoPiano() %>" piano="<%=(motivo.getDescrizionePiano()!=null) ? motivo.getDescrizionePiano().replaceAll("\"","*") : "" %>" type = "checkbox" name = "pianodialog" id="piano<%=motivo.getIdPiano() %>" value = "<%=motivo.getIdPiano()%>" <%= listaIdPiano.contains(motivo.getIdPiano()) || (motivo.getIdPiano()<=0 && listaIdAttivita.contains(motivo.getIdMotivoIspezione())) ? "checked=\"checked\"" : "" %>>
		
		</td>
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
	
		</div>
		
		<div id="containerTipoIsezioneSelected">
		<%
		if (TicketDetails.getTipoCampione() == 3 && TicketDetails.getAssignedDate().before(java.sql.Timestamp.valueOf(org.aspcf.modules.controlliufficiali.base.ApplicationProperties.getProperty("TIMESTAMP_NUOVA_GESTIONE_MOTIVO_ISPEZIONE_AUDIT"))))
		{
		%>
		<input type = "hidden" name = "auditTipo" value = "<%=TicketDetails.getAuditTipo()%>"/>
		<%
		Iterator <Integer> itKey = TicketDetails.getTipoAudit().keySet().iterator();
		while (itKey.hasNext())
		{
		%> 
		<input type = "hidden" name = "tipoAudit" value = "<%=itKey.next()%>"/>
		<%} 
		
		itKey = TicketDetails.getLisaElementibpi().keySet().iterator();
		while (itKey.hasNext())
		{
		%>
		
		<input type = "hidden" name = "bpi" value = "<%=itKey.next()%>"/>
		<%}
		itKey = TicketDetails.getLisaElementihaccp().keySet().iterator();
		while (itKey.hasNext())
		{
		%>
				<input type = "hidden" name = "haccp" value = "<%=itKey.next()%>"/>
		
		<%} 
		}
		else if (TicketDetails.getTipoCampione()==4 || TicketDetails.getTipoCampione() == 3)
		{
			Iterator <Integer> itKey =TicketDetails.getTipoIspezione().keySet().iterator();
			while (itKey.hasNext())
			{
				int vval = itKey.next() ;
			%>
			<input type = "hidden" name = "tipoIspezione" id = "tipoIspezione<%=vval %>" value = "<%=vval%>"/>
			<%
			}
			
		}
		else if (TicketDetails.getTipoCampione()==26)
		{%>
		<input type = "hidden" name = "auditTipo" value = "<%=TicketDetails.getAuditTipo()%>"/>
			<%				}		%>
	
	
		
		</div>
		