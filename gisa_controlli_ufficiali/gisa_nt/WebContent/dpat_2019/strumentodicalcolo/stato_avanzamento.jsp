<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStruttura"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>


<script type="text/javascript" src="javascript/jquery.miny_1.7.2.js"></script>
<script src="javascript/jquery-ui.js" type="text/javascript" ></script>

<link rel="stylesheet" type="text/css" href="css/theme.blue.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquery.tablesorter.pages.css"></link>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>
<script type="text/javascript" src="javascript/jquery.tablesorter.widgets.js"></script>

<script>


$(function(){
	
	 var pagerOptions = {
			    // target the pager markup - see the HTML block below
			    container: $(".pager"),
			    // output string - default is '{page}/{totalPages}'; possible variables: {page}, {totalPages}, {startRow}, {endRow} and {totalRows}
			    output: '{startRow} - {endRow} / {filteredRows} ({totalRows})',
			    // if true, the table will remain the same height no matter how many records are displayed. The space is made up by an empty
			    // table row set to a height to compensate; default is false
			    fixedHeight: true,
			    // remove rows from the table to speed up the sort of large tables.
			    // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
			    removeRows: false,
			    // go to page selector - select dropdown that sets the current page
			    cssGoto: '.gotoPage'
			  };

	 $("#tableSDC")
	 .tablesorter({
			theme: 'blue',
			headerTemplate : '{content} {icon}', // new in v2.7. Needed to add the bootstrap icon!
			widthFixed: true,
			widgets: ['zebra', 'filter']
		}).tablesorterPager(pagerOptions);
	 


	});
</script>


<html>
<style>
tbody tr:nth-child(odd) {
  background: #eee;
}
tbody tr:hover { /* th:hover also if you wish */
  background: yellow;
}
table, td, th {
    border: 1px solid #98bf21;
    vertical-align: middle;
    border-collapse: collapse;
    padding: 3px 7px 2px 7px;
}
th {
   background-color: green;
    color: white;
}
</style>
<body>
<h2> </h2>

<h4>STATO AVANZAMENTO ORGANIGRAMMA</h4>

<%
boolean filter = true;
ArrayList<DpatStruttura> strutture = (ArrayList<DpatStruttura>)request.getAttribute("strutture");

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
			<option value="30">30</option>
			<option value="40">40</option>
<option value="100">100</option>

			
		</select>
	</div>
	
<table id ="tableSDC" class="tableSorter">
  		<thead>
  			
  			
  			<tr class="tablesorter-headerRow" role="row">
		
<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="CERCA PER STATO" class="<%=(filter) ? "first-name filter-select" : "filter-false"%>"><div class="tablesorter-header-inner">STATO</div></th>
<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="CERCA PER ASL" class="<%=(filter) ? "first-name filter-select" : "filter-false"%>"><div class="tablesorter-header-inner">ASL</div></th>
<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="CERCA PER STRUTTURA"  class="<%=(filter) ? "filter-match tablesorter-header tablesorter-headerUnSorted" : "filter-false"%>"><div class="tablesorter-header-inner">STRUTTURA COMPLESSA</div></th>
</tr>
</thead>
	<tbody aria-relevant="all" aria-live="polite">
	<c:forEach var="struttura" items="${strutture}"> 
    	<tr>
        	<td>${struttura.descrizioneStato}</td>
			<td>${struttura.asl_stringa}</td>
			<td>${struttura.descrizione_lunga}</td>
        </tr>
     </c:forEach>
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
<option value="100">100</option>

			
		</select>
	</div>

