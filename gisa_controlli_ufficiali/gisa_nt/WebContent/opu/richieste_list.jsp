<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_list.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description:
  --%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.aziendeagricole.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="StabilimentiList" class="org.aspcfs.modules.opu.base.StabilimentoList" scope="request"/>
<jsp:useBean id="SearchOpuListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>



<link rel="stylesheet" type="text/css"
	href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css"
	href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>
<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>
<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/tableJqueryFilterOpu.js"></script>
<script src="javascript/jquery.searchable-1.0.0.min.js"></script>


  <link rel="stylesheet" type="text/css" media="screen" href="css/jquery.ui.combogrid.css"/>
  <script type="text/javascript" src="javascript/jquery.ui.combogrid.js"></script>
  


<script>
function openPopup(url){
	
		  var res;
	        var result;
	        	  window.open(url,'popupSelect',
	              'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
		}
</script>


<style>
.myButton {
	-moz-box-shadow:inset 0px 1px 0px 0px #f29c93 !important;
	-webkit-box-shadow:inset 0px 1px 0px 0px #f29c93 !important;
	box-shadow:inset 0px 1px 0px 0px #f29c93 !important;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #fe1a00), color-stop(1, #ce0100)) !important;
	background:-moz-linear-gradient(top, #fe1a00 5%, #ce0100 100%) !important;
	background:-webkit-linear-gradient(top, #fe1a00 5%, #ce0100 100%) !important;
	background:-o-linear-gradient(top, #fe1a00 5%, #ce0100 100%) !important;
	background:-ms-linear-gradient(top, #fe1a00 5%, #ce0100 100%) !important;
	background:linear-gradient(to bottom, #fe1a00 5%, #ce0100 100%) !important;
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#fe1a00', endColorstr='#ce0100',GradientType=0) !important;
	background-color:#fe1a00 !important;
	-moz-border-radius:6px !important;
	-webkit-border-radius:6px !important;
	border-radius:6px !important;
	border:1px solid #d83526 !important;
	display:inline-block !important;
	cursor:pointer !important;
	color:#ffffff !important;
	font-family:Arial !important;
	font-size:15px !important;
	font-weight:bold !important;
	padding:2px 24px !important;
	text-decoration:none !important;
	text-shadow:0px 1px 0px #b23e35 !important;
}
.myButton:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #ce0100), color-stop(1, #fe1a00)) !important;
	background:-moz-linear-gradient(top, #ce0100 5%, #fe1a00 100%) !important;
	background:-webkit-linear-gradient(top, #ce0100 5%, #fe1a00 100%) !important;
	background:-o-linear-gradient(top, #ce0100 5%, #fe1a00 100%) !important;
	background:-ms-linear-gradient(top, #ce0100 5%, #fe1a00 100%) !important;
	background:linear-gradient(to bottom, #ce0100 5%, #fe1a00 100%) !important;
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ce0100', endColorstr='#fe1a00',GradientType=0) !important;
	background-color:#ce0100 !important;
}
.myButton:active {
	position:relative !important;
	top:1px !important;
}


}



</style>



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

<table class="tablesorter">

			<thead>
				<tr class="tablesorter-headerRow" role="row">

					<th
						aria-label="Denominazione: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						data-placeholder="FILTRO PER DENOMINAZIONE"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">DENOMINAZIONE</div></th>

					<th 
						aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" 
						aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" 
						data-placeholder="FILTRA PER ASL" class="first-name filter-select">
						<div class="tablesorter-header-inner">ASL</div></th>

					<th
						aria-label="Numero registrazione ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER NUMERO REGISTRAZIONE"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">NUMERO REGISTRAZIONE</th>
					<th
						aria-label="Parita IVA ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER PARTITA IVA"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">PARTITA IVA</th>
					<th
						aria-label="Indirizzo ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER INDIRIZZO"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">SEDE PRODUTTIVA</th>
					<th
						aria-label="Codice Azienda: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						class="filter-false tablesorter-header"><div
							class="tablesorter-header-inner">OPERAZIONI</div></th>


				</tr>
			</thead>
	<tbody aria-relevant="all" aria-live="polite">		
			



<%
	Iterator j = StabilimentiList.iterator();
	if ( j.hasNext()  ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Stabilimento thisOrg = (Stabilimento)j.next();
%>
  <tr class="row<%= rowid %>">
    
	<td>
	   <%= toHtml(thisOrg.getOperatore().getRagioneSociale()) %>
 	</td>
	
	
        <td valign="top" nowrap><%= SiteIdList.getSelectedValue(thisOrg.getIdAsl()) %></td>

 
 <td valign="top" nowrap><%= toHtml2(thisOrg.getNumero_registrazione()) %></td>
 <td valign="top" nowrap><%=  toHtml2(thisOrg.getOperatore().getPartitaIva()) %></td>
 
  

		
	<td>
      <%= toHtml(thisOrg.getSedeOperativa().toString()) %>
	</td>
	
<td>
      <input type="button" class="myButton" value="VALIDA" onClick="openPopup('OpuStab.do?command=PrepareValidazione&stabId=<%=thisOrg.getIdStabilimento() %>')"/>
        <a href="#" onClick="openPopup('OpuStab.do?command=DetailsMinimale&stabId=<%=thisOrg.getIdStabilimento() %>')">Apri dettaglio</a>
	</td>		
		
      
	
  </tr>
<%} }%>
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
