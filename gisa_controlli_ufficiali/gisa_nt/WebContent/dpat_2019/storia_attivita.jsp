<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>



<%@page import="org.apache.poi.ss.formula.functions.Today"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatAttivita"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatIndicatore"%>
<%@page import="org.aspcfs.modules.dpat2019.base.*" %>
<%@page import="org.aspcfs.modules.dpat2019.*" %>



<script type="text/javascript" src="javascript/jquery.miny_1.7.2.js"></script>
<script   src="javascript/jquery-ui.js"></script>

<%@ include file="../initPage.jsp"%>


<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />
 
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterDialogRichiesteDaValidare.js"></script>

 
<%@page import="java.util.ArrayList,org.servlet.*" %>
 

<%
	ArrayList alPianiAttivita = (ArrayList)request.getAttribute("alPianiAttivita");
	Sezione sezione = (Sezione) request.getAttribute("sezione");

%>
<center>
	<font size="2px" style="color: red; font-weight: bold;" >
		versioni per PIANO-ATTIVITA codice univoco <u><%=((PianoAttivita)alPianiAttivita.get(0)).getCodiceAliasAttivita() %></u>
	</font>
	</center>
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
			<option value="<%=alPianiAttivita.size()%>">Tutti</option>
		</select> / <%=alPianiAttivita.size()%>
	</div>

  	<table id ="tableRichiesteDaValidare" class="tableSorter">
  		<thead>
  			
  			
  			<tr class="tablesorter-headerRow" role="row">
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">ANNO</div></th>
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">DESCRIZIONE</div></th>
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">CODICE UNIVOCO</div></th>
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">ATTIVO AL</div></th>

	</tr>
	</thead>
	
		<tbody aria-relevant="all" aria-live="polite">
		
		<%
		for (Object pianoAttivita : alPianiAttivita)
		{
			PianoAttivita piano = (PianoAttivita)pianoAttivita;
			String descrSezMadre = piano.getDescrSezMadre();
			String descrPianoAttivita = piano.getDescrizione();
			%>
			<tr>
			<td><%=piano.getAnno() %></td>
			<td>
				<font color="orange"><%=descrSezMadre %></font><br>
				<font color="green"><b>> </b><%=descrPianoAttivita %></font>
			</td>
			 
			<td><%=piano.getCodiceAliasAttivita()%></td>
			<td><%= (piano.getScadenza()!=null) ? toDateasString(piano.getScadenza()) : piano.getAnno()  %></td>
			
			</tr>
			
			<%
		}
		%>
		</tbody>


</table>
	