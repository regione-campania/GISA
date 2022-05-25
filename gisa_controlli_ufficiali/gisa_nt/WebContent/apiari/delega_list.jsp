<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="ext.aspcfs.modules.apicolture.actions.StabilimentoAction"%>
<%@page import="ext.aspcfs.modules.apiari.base.Delega"%>
<jsp:useBean id="DelegaList" class="ext.aspcfs.modules.apiari.base.DelegaList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />



<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterApi.js"></script>
<%@ include file="../initPage.jsp"%>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />


<script>
function cambiaCodiceFiscale(cf,opId)
{
	
	if(opId<=0)
		{
		 document.forms['sceltaTipoAttivita'].cf.value = cf ; 
		 document.forms['sceltaTipoAttivita'].opId.value = opId ; 
		
		 $( "#dialogVerifica" ).dialog('open');
		
		}
	else
		{
		//alert('ApicolturaAttivita.do?command=CambiaCodiceFiscale&cf='+cf+'&opId='+opId);
		
		location.href='ApicolturaAttivita.do?command=CambiaCodiceFiscale&cf='+cf+'&opId='+opId;
		}
	
}

function cambiaCodiceFiscalebycf(cf)
{
	
	
		location.href='ApicolturaAttivita.do?command=CambiaCodiceFiscale&cf='+cf;
		
	
}


</script>

<jsp:include page="./delega_add.jsp" />

<br>


<%
	if(request.getAttribute("esitiErroriParsingFile") != null && request.getAttribute("esitiInsert") != null)
	{
%>
		<br><font style="font-weight: bold;">Log file:</font>	
	    <%= ( (StringBuffer)request.getAttribute("esitiErroriParsingFile") ).toString() %>  
			   		 
			   		 <br><font style="font-weight: bold;">Log inserimenti:</font>
			   		 <%= ( (StringBuffer)request.getAttribute("esitiInsert") ).toString() %>
			   <%} %>
			   
			   
<!-- <div align="right"> -->
<%-- <%if (User.getSoggetto()!=null){ %><input type ="button" value="Seleziona Codice Fiscale Personale" onclick="cambiaCodiceFiscale('<%=User.getContact().getVisibilitaDelega() %>')"><%} %> --%>
<!-- </div> -->
<fieldset>
<legend>

<input type ="button" value="Nuova Delega" onclick="$( '#dialogDelega' ).dialog('open');">&nbsp;&nbsp;
</legend>
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


<table  class="tablesorter">

	<thead>
		<tr class="tablesorter-headerRow" role="row">

			<th aria-label="CODICE FISCALE DELEGATO No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER DETENTORE" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">CODICE FISCALE DELEGATO</div></th>
			<th aria-label="NOMINATIVO DELEGATO ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER NOMINATIVO" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">NOMINATIVO DELEGATO</th>
			<th aria-label="CODICE FISCALE DELEGANTE ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER CODICE FISCALE DELEGANTE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">CODICE FISCALE DELEGANTE</th>
			<th aria-label="NOMINATIVO DELEGANTE ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER NOMINATIVO DELEGANTE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">NOMINATIVO DELEGANTE</th>
			<th aria-label="STATO PRATICA ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER NOMINATIVO" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">STATO PRATICA</th>
			<th aria-label="NOMINATIVO DELEGATO ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER NOMINATIVO" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">DATA ASSEGNAZIONE DELEGA</th>
			<th aria-label="Codice Azienda: No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">Allegato</div></th>
			<th aria-label="Codice Azienda: No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">OPERAZIONI</div></th>
			
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">
	
	<%
	if(DelegaList.size()>0)
	{
		Iterator<Delega> itDelega = DelegaList.iterator();
		while (itDelega.hasNext())
		{
			
			Delega thisDelega = itDelega.next();
			
			if(thisDelega.getSoggetto_fisico_delegante()!=null && thisDelega.getDelegato()!=null)
			{
			%>
			<tr>
			<td><%=toHtml2(thisDelega.getCodice_fiscale_delegato()) %></td>
			<td><%=thisDelega.getDelegato().getContact().getNameFirst()+" "+thisDelega.getDelegato().getContact().getNameLast() %></td>
			<td><%=toHtml2(thisDelega.getCodice_fiscale_delegante()) %></td>
			<td><%=toHtml2(thisDelega.getSoggetto_fisico_delegante().getNome()+" "+thisDelega.getSoggetto_fisico_delegante().getCognome()) %></td>
			<td><%=(thisDelega.getIdStatoAttivita()==StabilimentoAction.API_STATO_DA_NOTIFICARE )  ? "IN RICHIESTA" :  (thisDelega.getIdStatoAttivita()==StabilimentoAction.API_STATO_VALIDATO ? "APPROVATO" : thisDelega.getIdStatoAttivita()==StabilimentoAction.API_STATO_INCOMPLETO  ? "DA COMPLETARE" : "CESSATO" )%></td>
			<td><%=toDateasString(thisDelega.getData_assegnazione_delega()) %></td>
			<td><a href="#" onclick="window.open('DelegaAction.do?command=DownloadDelega&id=<%=thisDelega.getId()%>', '_new', 'location=yes,height=570,width=520,scrollbars=yes,status=yes');" >Scarica delega</a> </td>
			
			<td>
			<%
			if (thisDelega.isEnabled())
			{
			%>
			<button onclick="cambiaCodiceFiscale('<%=thisDelega.getCodice_fiscale_delegante()%>',<%=thisDelega.getIdAttivita()%>)">Seleziona Codice Fiscale Corrente</button></td>
			<%}
			else{
				%>
				LA DELEGA E STATA TOLTA
				<%
			}
			%>
			</tr>
			<%
			}
		}
	}else
	{
	%>
	<tr>
	<td colspan="6">Nessuna delega Presente</td>
	</tr>
	
	<%} %>
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

<%@ include file="dialog_apicoltore_tipo_attivita.jsp" %>

