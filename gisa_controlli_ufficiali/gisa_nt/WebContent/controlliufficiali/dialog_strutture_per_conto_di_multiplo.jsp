<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@page import="java.util.HashMap"%>
<%@page import="org.aspcfs.modules.oia.base.OiaNodo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcf.modules.controlliufficiali.base.Piano"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.modules.vigilanza.base.Ticket"%>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>



<script>

function settaUnitaOperativaMultiple()
{
	 
	 var arr = $('input[name=uo_controlloSelect]:checked');
	 $('input[name=uo_controlloSelect]:checked').attr('uo');
	 
	  $("#listaStruttureMultiple").find("tr:gt(0)").remove();
	  
	  //alert(arr);
	  
	 for (i=0;i<arr.length;i++)
		 {
	   //alert(arr[i].value);
	   //alert(arr[i].getAttribute("uo"));
	   $("#listaStruttureMultiple").append('<tr valign="top"><td><input type="hidden" name="uo_controllo"  value="'+arr[i].value+'">'+arr[i].getAttribute("uo")+'</td></tr>');
		 }
		
}

$(function () {
	    
	
	
	
	 $( "#dialogPerContoDiMultiplo" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"LISTA STRUTTURE MULTIPLE DPAT",
	        width:850,
	        height:500,
	        draggable: false,
	        modal: true,
	        buttons:{
	        	 "FATTO": function() {settaUnitaOperativaMultiple(); $(this).dialog("close"); } ,
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

	<div id ="dialogPerContoDiMultiplo">
		<%
		ArrayList<OiaNodo> listaDialogMulti = (ArrayList<OiaNodo>)request.getAttribute("StrutturaAsl");
		
		HashMap<Integer,ArrayList<OiaNodo>> strutture_asl_regione = (HashMap<Integer,ArrayList<OiaNodo>>)application.getAttribute("StruttureOIA");
		
		ArrayList<OiaNodo> listaRegione = strutture_asl_regione.get(8);
		%>		


<font color="red">Attenzione! Di seguito sono riportate tutte le strutture presenti nello strumento di calcolo per cui è stato eseguito il "Salva e Chiudi".<br>
Qualora non fossero presenti le strutture desiderate, controllare che figurino correttamente nello strumento di calcolo e che quest'ultimo sia stato Salvato/Chiuso.
</font>
<br>
<table  class="tablesorter" id="tablelistastruttureMulti">

	<thead>
		<tr class="tablesorter-headerRow" role="row">
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO TIPO STRUTTURA" class="first-name filter-select"><div class="tablesorter-header-inner">ASL</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO TIPO STRUTTURA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">TIPOLOGIA STRUTTURA</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER DESCRIZIONE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">DESCRIZIONE STRUTTURA</div></th>
			
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER STRUTTURA APPARTENENZA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">STRUTTURA DI APPARTENENZA</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER DESCRIZIONE PIANO" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">&nbsp;</th>
					
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">
	
	<%
	ArrayList<OiaNodo> lista_uo_sel_cu = TicketDetails.getLista_unita_operative();
	for (OiaNodo nodoAsl : listaDialogMulti)
	{
		
		boolean selezionatoInCu = false ;
		
		boolean selezionato = false ;
		
		
		
		if(nodoAsl.getLista_nodi().size()>0)
		{
			for (OiaNodo nodoFiglio :nodoAsl.getLista_nodi() )
			{
				
				for (OiaNodo nodoSelCu : lista_uo_sel_cu)
				{
					if (nodoSelCu.getId()==nodoFiglio.getId())
						selezionatoInCu=true ;
				}
				
				
				if ( "6".equalsIgnoreCase(""+OrgDetails.getTipologia()+""))		{
					if (nodoAsl.getTipologia_struttura()!=12 && nodoFiglio.getTipologia_struttura()!=13 && nodoFiglio.getTipologia_struttura()!=14 ){
				%>
				
				<tr>
				<td><%= toHtml2(SiteIdList.getSelectedValue( nodoFiglio.getId_asl()))%></td>
				<td><%= toHtml2(nodoFiglio.getDescrizione_tipologia_struttura())%></td>
				<td><%=nodoFiglio.getDescrizione_lunga() %></td>
				<td><%=nodoAsl.getDescrizione_lunga() %></td>
				<td><input type="checkbox" <%=selezionatoInCu==true ? "checked" : "" %> onClick="if(document.getElementById('siteId') !=null && document.getElementById('siteId').value!=<%=nodoFiglio.getId_asl() %>) {alert('Questa struttura non corrisponde ad ASL CONTROLLO.'); this.checked='false'; return false; }" uo="<%=nodoAsl.getDescrizione_lunga().replaceAll("\"", "")+"->"+nodoFiglio.getDescrizione_lunga().replaceAll("\"", "")  %>" name = "uo_controlloSelect" value="<%=nodoFiglio.getId()%>"></td>
				</tr>
				
				<%
				selezionatoInCu = false ; }}
				else
				{
					if (nodoFiglio.getTipologia_struttura()!=13 && nodoFiglio.getTipologia_struttura()!=14)
					{
					%>
					<tr>
					<td><%= toHtml2(SiteIdList.getSelectedValue( nodoFiglio.getId_asl()))%></td>
					<td><%= toHtml2(nodoFiglio.getDescrizione_tipologia_struttura())%></td>
					<td><%=nodoFiglio.getDescrizione_lunga() %></td>
					<td><%=nodoAsl.getDescrizione_lunga() %></td>
					<td><input type="checkbox" <%=selezionatoInCu==true ? "checked" : "" %> onClick="if(document.getElementById('siteId') !=null && document.getElementById('siteId').value!=<%=nodoFiglio.getId_asl() %>) {alert('Questa struttura non corrisponde ad ASL CONTROLLO.'); this.checked='false'; return false; }" uo="<%=nodoAsl.getDescrizione_lunga().replaceAll("\"", "")+"->"+nodoFiglio.getDescrizione_lunga().replaceAll("\"", "")  %>" name = "uo_controlloSelect" value="<%=nodoFiglio.getId()%>"></td>
					</tr>
					
					<%}
					selezionatoInCu = false ;
					
				}
			}
			
			
			for (OiaNodo nodoFiglio :nodoAsl.getLista_nodi() )
			{
				if(nodoFiglio.getLista_nodi().size()>0)
				{
					
					
					
									
					for (OiaNodo nipote :nodoFiglio.getLista_nodi() )
					{
						for (OiaNodo nodoSelCu : lista_uo_sel_cu)
						{
							if (nodoSelCu.getId()==nipote.getId())
								selezionatoInCu=true ;
						}
						if ( "6".equalsIgnoreCase(""+OrgDetails.getTipologia()+"") )
						{
							if (nipote.getTipologia_struttura()!=12 &&  nipote.getTipologia_struttura()!=13 && nipote.getTipologia_struttura()!=14  ){
						%>
						
					<tr>
				
			<td><%= toHtml2(SiteIdList.getSelectedValue( nipote.getId_asl()))%></td>
				<td><%= toHtml2(nipote.getDescrizione_tipologia_struttura())%></td>
				<td><%=nipote.getDescrizione_lunga() %></td>
				<td><%=nodoFiglio.getDescrizione_lunga() %></td>
				<td><input type="checkbox" <%=selezionatoInCu==true ? "checked" : "" %> onClick="if(document.getElementById('siteId') !=null && document.getElementById('siteId').value!=<%=nipote.getId_asl() %>) {alert('Questa struttura non corrisponde ad ASL CONTROLLO.'); this.checked='false'; return false; }" uo="<%=nodoFiglio.getDescrizione_lunga().replaceAll("\"", "")+"->"+nipote.getDescrizione_lunga().replaceAll("\"", "")  %>" name = "uo_controlloSelect" value="<%=nipote.getId()%>"></td>
				</tr>
						<% selezionatoInCu = false ; 
						}}
						else
						{
							
							if (nipote.getTipologia_struttura()!=13 && nipote.getTipologia_struttura()!=14)
							{
							%>
							<tr>
				
			<td><%= toHtml2(SiteIdList.getSelectedValue( nipote.getId_asl()))%></td>
				<td><%= toHtml2(nipote.getDescrizione_tipologia_struttura())%></td>
				<td><%=nipote.getDescrizione_lunga() %></td>
				<td><%=nodoFiglio.getDescrizione_lunga() %></td>
				<td><input type="checkbox" <%=selezionatoInCu==true ? "checked" : "" %> onClick="if(document.getElementById('siteId') !=null && document.getElementById('siteId').value!=<%=nipote.getId_asl() %>) {alert('Questa struttura non corrisponde ad ASL CONTROLLO.'); this.checked='false'; return false; }" uo="<%=nodoFiglio.getDescrizione_lunga().replaceAll("\"", "")+"->"+nipote.getDescrizione_lunga().replaceAll("\"", "")  %>" name = "uo_controlloSelect" value="<%=nipote.getId()%>"></td>
				</tr>
						<% selezionatoInCu = false ;
							
						}
						}
					}
				
				
			}
			}
		}
		else
		{
			if ("6".equalsIgnoreCase(""+OrgDetails.getTipologia()+"") )
			{
				if (nodoAsl.getTipologia_struttura()!=12 && nodoAsl.getTipologia_struttura()!=13 && nodoAsl.getTipologia_struttura()!=14){
			%>
			<tr>
			<td><%= toHtml2(SiteIdList.getSelectedValue( nodoAsl.getId_asl()))%></td>
				<td><%= toHtml2(nodoAsl.getDescrizione_tipologia_struttura())%></td>
			<td><%= nodoAsl.getDescrizione_tipologia_struttura()%></td>
			<td><%=nodoAsl.getDescrizione_lunga() %></td>
			
				<td><input type="checkbox" <%=selezionatoInCu==true ? "checked" : "" %> onClick="if(document.getElementById('siteId') !=null && document.getElementById('siteId').value!=<%=nodoAsl.getId_asl() %>) {alert('Questa struttura non corrisponde ad ASL CONTROLLO.'); this.checked='false'; return false; }" uo="<%=nodoAsl.getDescrizionePadre().replaceAll("\"", "")%>" name = "uo_controlloSelect" value="<%=nodoAsl.getId()%>"></td>
			</tr>
			<%
			selezionatoInCu = false ;}}
			else
			{
				%>
<!-- 				<tr> -->
<!-- 			<td colspan="5">ATTENZIONE! OCCORRE PER POTER INSERIRE I CONTROLLI UFFICIALI OCCORRE SALVARE E CHIUDERE LO STRUMENTO DI CALCOLO PER LA STRUTTURA COMPLESSA</td> -->
<!-- 			</tr> -->
			<%
			selezionatoInCu = false ;
			}
		}
	}
	
	if (  "6".equalsIgnoreCase(""+OrgDetails.getTipologia()+""))		{
		boolean selezionatoInCu = false;
	for (OiaNodo nodoRegione : listaRegione)
	{
		out.print(nodoRegione.getDescrizione_lunga());
		
		for (OiaNodo nodoSelCu : lista_uo_sel_cu)
		{
		if (nodoSelCu.getId()==nodoRegione.getId())
			selezionatoInCu=true ;
		}
		%>
		<tr>
		<td><%= toHtml2(SiteIdList.getSelectedValue( nodoRegione.getId_asl()))%></td>
				<td><%= toHtml2(nodoRegione.getDescrizione_tipologia_struttura())%></td>
			<td><%= nodoRegione.getDescrizione_tipologia_struttura()%></td>
			<td><%=nodoRegione.getDescrizione_lunga() %></td>
			
				<td><input type="checkbox" <%=selezionatoInCu==true ? "checked" : "" %> onClick="if(document.getElementById('siteId') !=null && document.getElementById('siteId').value!=<%=nodoRegione.getId_asl() %>) {alert('Questa struttura non corrisponde ad ASL CONTROLLO.'); this.checked='false'; return false; }" uo="<%=nodoRegione.getDescrizionePadre().replaceAll("\"", "")%>" name = "uo_controlloSelect" value="<%=nodoRegione.getId()%>"></td>
	</tr>
		<%
		selezionatoInCu = false;
	}
	}
	
	%>
	
	</tbody>
	
	</table>
	
	
	
		</div>
		
		
	
	
	