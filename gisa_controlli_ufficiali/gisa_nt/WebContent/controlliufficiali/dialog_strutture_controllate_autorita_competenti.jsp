<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@page import="org.aspcfs.modules.oia.base.OiaNodo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcf.modules.controlliufficiali.base.Piano"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.modules.vigilanza.base.Ticket"%>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>



<script>

$(function () {
	    
	
	function settaStruttura()
	{
		
		document.getElementById('struttureControllateDiv').innerHTML="" ;
		 
		 $('input[name=strutturaControllata_]:checked').val();
		var array = $('input[name=strutturaControllata_]:checked');
		 text = "" ;
		for (i=0 ; i< array.length ; i++)
		{
			text +="<br>"+parseInt(i+1)+". "+array[i].getAttribute('uo')+"<br>";
			
			 $("#struttureControllateDiv").append('<input type="hidden" name="strutturaControllata"  value="'+array[i].value+'"/>');
			
		}
		
		 $("#struttureControllateDiv").append(text);
		
	}
	
	 $( "#dialogAutoritaCompetenti" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"LISTA STRUTTIRE SOTTOPOSTE A CONTROLLO",
	        width:850,
	        height:500,
	        draggable: false,
	        modal: true,
	        buttons:{
	        	 "FATTO": function() {settaStruttura();$(this).dialog("close");} ,
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

	<div id ="dialogAutoritaCompetenti">
		<%
		ArrayList<OiaNodo> listaDialogAc = (ArrayList<OiaNodo>)request.getAttribute("ListStruttureAComp");
		%>		
	


<font color="red">Attenzione! Di seguito sono riportate tutte le strutture presenti nello strumento di calcolo per cui è stato eseguito il "Salva e Chiudi".<br>
Qualora non fossero presenti le strutture desiderate, controllare che figurino correttamente nello strumento di calcolo e che quest'ultimo sia stato Salvato/Chiuso.
</font>
<br>

<table  class="tablesorter" id="tablelistastruttureac">

	<thead>
		<tr class="tablesorter-headerRow" role="row">
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO TIPO STRUTTURA" class="first-name filter-select"><div class="tablesorter-header-inner">ASL</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO TIPO STRUTTURA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">TIPOLOGIA STRUTTURA</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER DESCRIZIONE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">DESCRIZIONE STRUTTURA</div></th>
			
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER STRUTTURA APPARTENENZA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">STRUTTURA DI APPARTENENZA</div></th>
					
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">
	
	<%
	
	if (listaDialogAc!=null)
	for (OiaNodo nodoAsl : listaDialogAc)
	{
		boolean checked= false ;
		checked = TicketDetails.getListaStruttureControllareAutoritaCompetenti().containsKey(nodoAsl.getId());
			
				%>
				
				<tr>
				<td><%= toHtml2(SiteIdList.getSelectedValue( nodoAsl.getId_asl()))%></td>
				<td><%= toHtml2(nodoAsl.getDescrizione_tipologia_struttura())%></td>
				<td><%=nodoAsl.getDescrizione_lunga() %></td>
				<td><input type="checkbox" uo="<%=nodoAsl.getDescrizione_lunga().replaceAll("\"", "") %>" <%=(checked) ? "checked" :"" %> name = "strutturaControllata_" value="<%=nodoAsl.getId()%>"></td>
				</tr>
				
				<%
			
			
			
	
	}
	%>
	
	</tbody>
	
	</table>
	
	
	
		</div>
		
		
	
	
	