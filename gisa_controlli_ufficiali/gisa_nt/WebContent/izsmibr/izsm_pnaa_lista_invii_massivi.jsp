<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.izsmibr.base.PrelievoPNAA"%>
<%@ page import="java.util.*,org.aspcfs.utils.*"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../initPage.jsp"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />



<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterDialogRichiesteDaValidare.js"></script>

<script>
function downloadInvio(idInvio){
	window.open('GestioneAllegatiInvii.do?command=DownloadByInvio&idInvio='+idInvio+'&tipoCertificato=PNAACA&tipoDocumento=xlsx');
}

</script>
  
	 


<br>

  <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	

<dhv:container name="inviocupnaa" selected="Lista Invii PNAA" object="">


<table class="details" width="100%">
		 	<tr>
  				<th style="font-size:12px; text-align: center">Lista Invii Massivi Effettuati verso SINVSA</th>
  			</tr>
  			
 </table>
 
 <%
 ArrayList<PrelievoPNAA> listaInvii = ( ArrayList<PrelievoPNAA> ) request.getAttribute("listaInvii");
 %>
 
 <div class="pager">
		Page: <select class="gotoPage"></select>		
		<img src="javascript/img/first.png" class="first" alt="First" title="First page" />
		<img src="javascript/img/prev.png" class="prev" alt="Prev" title="Previous page" />
		<img src="javascript/img//next.png" class="next" alt="Next" title="Next page" />
		<img src="javascript/img/last.png" class="last" alt="Last" title= "Last page" />
		<select class="pagesize">
			<option value="10">10</option>
			<option value="20">20</option>
			<option value="30">40</option>
			<option value="40">40</option>
			<option value="<%=listaInvii.size()%>">Tutti</option>
		</select> / <%=listaInvii.size()%>
	</div>


  	<table id ="tableRichiesteDaValidare" class="tableSorter">
  		<thead>
  			
  			
  			<tr class="tablesorter-headerRow" role="row">
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">ID</div></th>
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER DATA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">DATA</div></th>
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER CARICATO DA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">CARICATO DA</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO" class="filter-false tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">FILE INVIATO</div></th>
<!--   			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">ERRORE CARICAMENTO</div></th> -->
<!--   			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">ESITo PDF</div></th> -->
<!--   			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">KO</div></th> -->
	</tr>
	</thead>
	
	
 		<tbody aria-relevant="all" aria-live="polite">
	
	
	<% 
	
		
		if ( listaInvii.size() > 0 ) {
			
				for ( int i=0; i< listaInvii.size(); i++ ) {
				
					
	%>
	
			<tr>
				<td align="right"><a href="#" onClick="loadModalWindow(); window.location.href='GestioneInvioPNAA.do?command=ListaInvii&idImport=<%=listaInvii.get(i).getIdImport()%>'"><%=listaInvii.get(i).getIdImport() %></a></td>
				<td align="right"><%=  toDateasStringWitTime(listaInvii.get(i).getEntered()) %></td>
				<td align="right"><dhv:username id="<%=listaInvii.get(i).getEnteredBy() %>"/></td>
				<td align="right"><a href="#" onClick="downloadInvio('<%=listaInvii.get(i).getIdImport()%>')">Download</a></td>
<!-- 				<td align="right"></td> -->
<!-- 				<td align="right"></td> -->
<!-- 				<td align="right"></td> -->
				
				
			</tr>
			<% } %>
		<% 
			
		} else { %>
		  <tr class="containerBody">
      			<td colspan="5">
        			<dhv:label name="">Nessun import eseguito.</dhv:label>
      			</td>
    	  </tr>
		<% } %> 
		
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
			<option value="30">40</option>
			<option value="40">40</option>
			<option value="<%=listaInvii.size()%>">Tutti</option>
		</select> / <%=listaInvii.size()%>
	</div>
 

</dhv:container>
	