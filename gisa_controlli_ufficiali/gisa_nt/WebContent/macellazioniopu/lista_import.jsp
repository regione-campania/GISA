<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="listaImport" class="org.aspcfs.modules.macellazioniopu.base.ImportLogList" scope="request"/>
<jsp:useBean id="downloadURL" class="java.lang.String" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>
<jsp:useBean id="altId" class="java.lang.String" scope="request"/>
<jsp:useBean id="op" class="java.lang.String" scope="request"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@page import="org.aspcfs.modules.macellazioniopu.base.ImportLog"%>


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterDialogImportMacelli.js"></script>

<script>
function openPopup(link){
	
		  var res;
	        var result;
	        
	      //  if (document.all) {
	        	  window.open(link,'popupSelect',
	              'height=400px,width=400px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

		}
		</script>
		
 <%! public static String fixData(String timestring)
  {
	  String toRet = "";
	  if (timestring == null)
		  return toRet;
	  String anno = timestring.substring(0,4);
	  String mese = timestring.substring(5,7);
	  String giorno = timestring.substring(8,10);
	  String ora = timestring.substring(11,13);
	  String minuto = timestring.substring(14,16);
	  String secondi = timestring.substring(17,19);
	  toRet =giorno+"/"+mese+"/"+anno+" "+ora+":"+minuto+":"+secondi;
	  return toRet;
	  
  }%>

    <br>
    <% String obj = "OrgDetails"; %>
  	<% String param1 = "altId=" + altId;   %>

<% String param ="";
	param = param1;

%>	
  <dhv:container name="<%=op %>" selected="importmacellazioni" object="<%=obj %>"  param="<%= param %>">
  
  			
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
		<option value="<%=listaImport.size()%>">Tutti</option>
		</select> / <%=listaImport.size()%>
</div>
<table  class="tablesorter" id = "tablelistaimportmacelli">
  
<thead>
		<tr class="tablesorter-headerRow" role="row">
		
		
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER DATA" class="sorter-shortDate dateFormat-ddmmyyyy"><div class="tablesorter-header-inner">Data import</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER DATA" class="sorter-shortDate dateFormat-ddmmyyyy"><div class="tablesorter-header-inner">Data macellazione</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="Esito" class="first-name filter-select">Esito import</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER NOTE" class="sorter-shortDate dateFormat-ddmmyyyy"><div class="tablesorter-header-inner">Note import</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER UTENTE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Importato da</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO" class="filter-false tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Recupera file</div></th>
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">
			
			<%

	if (listaImport.size()>0) {
		for (int i=0;i<listaImport.size(); i++){
			ImportLog log = (ImportLog) listaImport.get(i);
			
			%>
			
			<tr>
			<td><%=fixData(log.getDataImport().toString()) %></td> 
			<td><%=log.getDataMacellazione() %></td> 
			<td><%if (log.isEsitoImport()) { %><font color="green">OK</font><%} else { %><font color="red">KO</font><%} %></td> 
			<td><%=log.getErroriImport() %></td> 
			<td> <dhv:username id="<%= log.getUtenteImport() %>"></dhv:username></td> 
			<td><a href="#" onClick="openPopup('MacellazioniImport.do?command=DownloadImport&idImport=<%=log.getId()%>')">Download</a></td> 
		</tr>
		<%} } else {%>
		<tr><td colspan="6"> Non sono stati generati import.</td></tr> 
		
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
		<option value="30">30</option>
		<option value="40">40</option>
		<option value="<%=listaImport.size()%>">Tutti</option>
	</select> / <%=listaImport.size()%>
</div>


		</dhv:container>

</body>
</html>