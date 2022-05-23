<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@page import="it.us.web.bean.endpointconnector.*"%>


<link rel="stylesheet" type="text/css" href="js/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="js/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>


<script src="js/jquery/jquery-1.8.2.js"></script>
<script src="js/jquery-ui.js"></script>


<script type="text/javascript" src="js/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="js/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="js/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="js/jquerypluginTableSorter/tableJqueryFilterDialogEpc.js"></script>

<div align="center">
		<a href="Index.us" style="margin: 0px 0px 0px 50px">Indietro</a>
	</div>
<br/><br/>

<% EndPointConnectorList lista = (EndPointConnectorList) request.getAttribute("listaEndPoint"); %>

<table>


<tr>
<td align="left">
					<image src="images/adobe-icon.png"/><br>
					<a href="note configurazione guc.pdf" target="_blank" style="color:black">Note per la configurazione</a>
				</td>	
				</tr>
</table>


<table  class="tablesorter" id = "tablelistaendpoint">

<thead>
		<tr class="tablesorter-headerRow" role="row">
		
		
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="ID" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Id</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="TUTTI" class="first-name filter-select"><div class="tablesorter-header-inner">EndPoint</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="TUTTI" class="first-name filter-select"><div class="tablesorter-header-inner">DataSource</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="TUTTE" class="first-name filter-select"><div class="tablesorter-header-inner">Operazione</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="SQL" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Sql</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="RELOAD" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Reload</div></th>
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">


<% for (int i = 0; i< lista.size(); i++) {  
EndPointConnector epc = (EndPointConnector) lista.get(i);
%>
<tr style="border:1px solid black;">
<td style="border:1px solid black;"><%=epc.getId() %></td>
<td style="border:1px solid black;"><%=epc.getEndPoint().getNome() %></td>
<td style="border:1px solid black;"><%=epc.getEndPoint().getDataSourceMaster() %> / <%=epc.getEndPoint().getDataSourceSlave() %></td>
<td style="border:1px solid black;"><%=epc.getOperazione().getNome() %></td>
<td style="border:1px solid black;"><%=epc.getSql() %></td>
<td style="border:1px solid black;"><%=epc.getUrlReloadUtenti() %></td>
</tr>

<%} %>

</tbody>
	
	</table>
	


