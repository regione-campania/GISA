<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%--Pagina JSP creata da Francesco --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="org.aspcfs.utils.*" %>
<jsp:useBean id="BaseList" class="org.aspcfs.utils.web.CustomLookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="DisplayFieldId" class="java.lang.String" scope="request"/>
<jsp:useBean id="DisplayFieldId2" class="java.lang.String" scope="request"/>
<jsp:useBean id="Table" class="java.lang.String" scope="request"/>
<jsp:useBean id="FiltroDesc" class="java.lang.String" scope="request"/>

<jsp:useBean id="FiltroDesc2" class="java.lang.String" scope="request"/>
<jsp:useBean id="FiltroDesc3" class="java.lang.String" scope="request"/>


<jsp:useBean id="LookupSelectorInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script   src="javascript/jquery-ui.js"></script>



<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script>
function checkFiltro() {
  //cancello eventuali spazi all'inizio e alla fine del testo
  var desc = leftTrim(rightTrim(document.elementListView.filtroDesc.value));
 
  document.elementListView.filtroDesc.value = desc;
 //if (desc != "" || desc2!=""){
    elementListView.submit();  
  //} else {
    //document.elementListView.filtroDesc.focus();  
  //}
}
 
function leftTrim(stringa) {
  while (stringa.substring(0,1) == ' ') {
    stringa = stringa.substring(1, stringa.length);
  }
  return stringa;
}

function rightTrim(stringa) {
  while (stringa.substring(stringa.length-1, stringa.length) == ' ') {
    stringa = stringa.substring(0,stringa.length-1);
  }
  return stringa;
}

</script>



<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterDialogAllerte.js"></script>







<%@ include file="initPage.jsp" %>
<body>

<br />

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
		<option value="<%=BaseList.size()%>">Tutte</option>
	</select> / <%=BaseList.size()%>
</div>



<table  class="tablesorter" id = "tablelistaallerte">
  
<thead>
		<tr class="tablesorter-headerRow" role="row">
		
		
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER IDENTIFICATIVO" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Identificativo</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER DESCRIZIONE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Descrizione</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER DATA" class="sorter-shortDate dateFormat-ddmmyyyy"><div class="tablesorter-header-inner">Data</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER DATA LISTA" class="sorter-shortDate dateFormat-ddmmyyyy"><div class="tablesorter-header-inner">Data lista</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER NOME FORNITORE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Nome Fornitore</div></th>
<!-- 			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER DATA CHIUSURA" class="sorter-shortDate dateFormat-ddmmyyyy"><div class="tablesorter-header-inner">Data chiusura</div></th> -->
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">
			
			<%
  Iterator j = BaseList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      CustomLookupElement thisElt = (CustomLookupElement)j.next();
     
    
      String description = thisElt.getValue("id_allerta");
      String data = thisElt.getValue("assigned_date");
      String shortDescription = thisElt.getValue("problem");
      String ticketid = thisElt.getValue("ticketid");
   //   String unitaMisura = thisElt.getValue("unitamisura");
      String unitaMisura = "Kilogrammi";
      String idLdd = thisElt.getValue("id_ldd");
      String dataLista = thisElt.getValue("data_lista_ldd");
      String nomeFornitore = thisElt.getValue("nome_fornitore_ldd");
      String dataChiusura = thisElt.getValue("data_chiusura_ldd");
      //int code = codeString.startsWith("--") ? -1 : Integer.parseInt(codeString);
  %>
			
			<tr class="row<%= rowid %>">
    <td valign="center">
           
        <a href="javascript:setParentValue3_allerta3('<%= DisplayFieldId %>','<%= StringUtils.jsStringEscape(description.replaceAll("'"," ")) %>','<%= DisplayFieldId2 %>','<%= StringUtils.jsStringEscape(shortDescription).replaceAll("\"","").replaceAll("'"," ") %>','<%= StringUtils.jsStringEscape(ticketid) %>','<%=unitaMisura %>', '<%=idLdd%>', '<%= StringUtils.jsStringEscape(nomeFornitore).replaceAll("\"","").replaceAll("'"," ") %>', '<%=toDateasStringFromString(dataLista) %>', '<%=toDateasStringFromString(dataChiusura)%>' );">
        <%= toHtml(description) %>
      </a>
      
      
    </td>
    <td valign="center">
     <%= toHtml(shortDescription) %>
    </td>
     <td valign="center">
     <%= toDateasStringFromString(data) %>
    </td>
    
    <td valign="center">
     <%= toDateasStringFromString(dataLista) %>
    </td>
    <td valign="center">
     <%= toHtml(nomeFornitore) %>
    </td>
<!--     <td valign="center"> -->
<%--      <%= toDateasStringFromString(dataChiusura) %> --%>
<!--     </td> -->
    
  </tr>
  
		<%} } else {%>
		<tr><td colspan="5"> Non sono presenti allerte.</td></tr>
		
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
		<option value="<%=BaseList.size()%>">Tutte</option>
	</select> / <%=BaseList.size()%>
</div>







</body>
