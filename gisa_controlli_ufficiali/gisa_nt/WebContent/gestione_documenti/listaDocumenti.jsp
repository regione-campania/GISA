<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="listaDocumenti" class="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleDocumentoList" scope="request"/>
<jsp:useBean id="downloadURL" class="java.lang.String" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>
<jsp:useBean id="StabilimentoRichiestaDettaglio" class="org.aspcfs.modules.suap.base.Stabilimento" scope="request"/>
<jsp:useBean id="OperatoreRichiesta" class="org.aspcfs.modules.suap.base.Operatore" scope="request"/>
<jsp:useBean id="StabilimentoSintesisDettaglio" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>
<jsp:useBean id="OperatoreSintesis" class="org.aspcfs.modules.sintesis.base.SintesisOperatore" scope="request"/>

<jsp:useBean id="StabilimentoAnagraficaDettaglio" class="org.aspcfs.modules.gestioneanagrafica.base.Stabilimento" scope="request"/>
<jsp:useBean id="OperatoreAnagrafica" class="org.aspcfs.modules.gestioneanagrafica.base.Impresa" scope="request"/>

<jsp:useBean id="orgId" class="java.lang.String" scope="request"/>
<jsp:useBean id="ticketId" class="java.lang.String" scope="request"/>
<jsp:useBean id="op" class="java.lang.String" scope="request"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleDocumento"%>


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterDialogDocumenti.js"></script>


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
<% 
String obj = "OrgDetails";
String param0 = "id=" + ticketId; 
String param1 = "orgId=" + orgId;  
String param2 = "stabId=" ;
String param3 = "altId=" ;

String param ="";
if (op.equals("allerte"))
	param = param0;
else if (op.equals("gestioneanagrafica") && StabilimentoDettaglio!=null && StabilimentoDettaglio.getAltId()>0){
	param = param2+StabilimentoDettaglio.getIdStabilimento();
	obj = "Operatore";
}
else if (op.equals("gestioneanagraficanoscia") && StabilimentoDettaglio!=null && StabilimentoDettaglio.getAltId()>0){
	param = param2+StabilimentoDettaglio.getIdStabilimento();
	obj = "Operatore";
}
else if (OrgDetails!=null && OrgDetails.getOrgId()>0)
	param = param1;
else if (StabilimentoDettaglio!=null && StabilimentoDettaglio.getIdStabilimento()>0){
	param = param2+StabilimentoDettaglio.getIdStabilimento()+"&opId="+StabilimentoDettaglio.getIdOperatore();
	obj = "Operatore";
}
else if (StabilimentoRichiestaDettaglio!=null && StabilimentoRichiestaDettaglio.getAltId()>0){
	param = param3+StabilimentoRichiestaDettaglio.getAltId()+"&opId="+StabilimentoRichiestaDettaglio.getIdOperatore();
	obj = "OperatoreRichiesta";
}
else if (StabilimentoSintesisDettaglio!=null && StabilimentoSintesisDettaglio.getAltId()>0){
	param = param3+StabilimentoSintesisDettaglio.getAltId()+"&opId="+StabilimentoSintesisDettaglio.getIdOperatore();
	obj = "OperatoreSintesis";
}
else if (StabilimentoAnagraficaDettaglio!=null && StabilimentoAnagraficaDettaglio.getAlt_id()>0){
	param = param3+StabilimentoAnagraficaDettaglio.getAlt_id()+"&opId="+StabilimentoAnagraficaDettaglio.getImpresa().getId();
	obj = "OperatoreAnagrafica";
}
%>	
  <dhv:container name="<%=op %>" selected="Documenti PDF" object="<%=obj %>"  param="<%= param %>">
  
  			
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
		<option value="<%=listaDocumenti.size()%>">Tutti</option>
		</select> / <%=listaDocumenti.size()%>
</div>
<table  class="tablesorter" id = "tablelistadocumenti">
  
<thead>
		<tr class="tablesorter-headerRow" role="row">
		
		
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER CODICE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Codice Doc.</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER TIPO" class="first-name filter-select"><div class="tablesorter-header-inner">Tipo</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER INFO" class="sorter-shortDate dateFormat-ddmmyyyy"><div class="tablesorter-header-inner">Info</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER DATA" class="sorter-shortDate dateFormat-ddmmyyyy"><div class="tablesorter-header-inner">Data creazione</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER UTENTE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Generato da</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO" class="filter-false tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Recupera</div></th>
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">
			
			<%

	if (listaDocumenti.size()>0) {
		for (int i=0;i<listaDocumenti.size(); i++){
			DocumentaleDocumento doc = (DocumentaleDocumento) listaDocumenti.get(i);
			
			%>
			
			<tr>
			<td><%= (doc.getIdHeader()!=null && !doc.getIdHeader().equals("null")) ? doc.getIdHeader() : doc.getIdDocumento() %></td> 
			<td><%= doc.getTipo() %></td> 
			<td><%= (doc.getExtra()!=null && !doc.getExtra().equals("null")) ? doc.getExtra() : "&nbsp;" %></td> 
			<td><%= fixData(doc.getDataCreazione()) %></td> 
			<td> <dhv:username id="<%= doc.getUserId() %>"></dhv:username>
			
			</td> 
			<td>
			
			<a href="GestioneDocumenti.do?command=DownloadPDF&codDocumento=<%=doc.getIdHeader()%>&idDocumento=<%=doc.getIdDocumento() %>"><input type="button" value="DOWNLOAD"></input></a>
			
			</td> 
		</tr>
		<%} } else {%>
		<tr><td colspan="5"> Non sono stati generati documenti.</td></tr>
		
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
		<option value="<%=listaDocumenti.size()%>">Tutti</option>
	</select> / <%=listaDocumenti.size()%>
</div>


		</dhv:container>

</body>
</html>