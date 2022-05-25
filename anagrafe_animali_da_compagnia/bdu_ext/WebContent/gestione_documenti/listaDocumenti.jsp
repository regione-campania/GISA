<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ include file="../../initPage.jsp"%>
<jsp:useBean id="listaDocumenti" class="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleDocumentoList" scope="request"/>
<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleDocumento"%>

<jsp:useBean id="downloadURL" class="java.lang.String" scope="request"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*, org.aspcfs.modules.base.*, org.aspcfs.modules.registrazioniAnimali.base.*" %>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Animale" class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request"/>


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script type="text/javascript">
function openVerificaGlifo(){
	var res;
	var result;
		window.open('GestioneDocumenti.do?command=Verifica','popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>

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
  
<% String param1 = "idAnimale=" + Animale.getIdAnimale() +"&idSpecie=" + Animale.getIdSpecie();
   %>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="AnimaleAction.do?command=Search"><dhv:label name="">Ricerca animali</dhv:label></a> > 
<a href="AnimaleAction.do?command=Details&animaleId=<%=Animale.getIdAnimale()%>&idSpecie=<%=Animale.getIdSpecie()%>"><dhv:label name="">Dettagli animale</dhv:label></a> >
<dhv:label name="">Documenti</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="animale" selected="Documenti" object="Animale" param="<%=param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>

    <br>
  
  <div class="pager">
	Page: <select class="gotoPage"></select>		
	<img src="javascript/img/first.png" class="first" alt="First" title="First page" />
	<img src="javascript/img/prev.png" class="prev" alt="Prev" title="Previous page" />
	<img src="javascript/img//next.png" class="next" alt="Next" title="Next page" />
	<img src="javascript/img/last.png" class="last" alt="Last" title= "Last page" />
	<select class="pagesize">
		<option value="10">10</option>
		<option value="20">20</option>
		<option value="<%=listaDocumenti.size()%>">Tutti</option>
	</select> / <%=listaDocumenti.size()%>
</div>
<table  class="tablesorter" id = "tablelistadocumenti">
  
<thead>
		<tr class="tablesorter-headerRow" role="row">
		
		
		<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER CODICE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Codice Doc.</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER TIPO" class="first-name filter-select"><div class="tablesorter-header-inner">Tipo</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER DATA" class="sorter-shortDate dateFormat-ddmmyyyy"><div class="tablesorter-header-inner">Data creazione</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER UTENTE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Generato da</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO" class="filter-false tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">Recupera</div></th>
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">
			
			<%
	
	if (listaDocumenti.size()>0){
		for (int i=0;i<listaDocumenti.size(); i++){
			DocumentaleDocumento doc = (DocumentaleDocumento) listaDocumenti.get(i);
			
			//CAMBIO COLORE A SECONDA DEL TIPO
			String color="\"#FFFFFF\"";
			if (doc.getTipo().equals("PrintCertificatoIscrizione")){
					color="\"#E5E4E2\"";}
			else if (doc.getTipo().equals("PrintRichiestaIscrizione")){
					color="\"#FFCBA4\"";}
			else if (doc.getTipo().equals("PrintRichiestaCampioni")){
					color="\"#FFE5B4\"";}
			else if (doc.getTipo().equals("PrintRichiestaCampioniRabbia")){
					color="\"#FFF5EE\"";}
			else if (doc.getTipo().equals("PrintDichiarazioneDecesso")){
				color="\"#C0C0C0\"";}
			else if (doc.getTipo().equals("PrintCertificatoDecesso")){
				color="\"#969696\"";}
			else if (doc.getTipo().equals("PrintCertificatoVaccinazioneAntiRabbia")){
				color="\"#90f030\"";}
			
			%>
			
			<tr>
			<td bgcolor=<%=color %>><%= (doc.getIdHeader()!=null && !doc.getIdHeader().equals("null")) ? doc.getIdHeader() : doc.getIdDocumento() %></td> 
			<td bgcolor=<%=color %>><%= doc.getTipo() %> <%if (doc.isStatico()){%> <b>[Statico]</b><%}%></td> 
			<td bgcolor=<%=color %>><%= fixData(doc.getDataCreazione()) %></td> 
			<td bgcolor=<%=color %>> <dhv:username id="<%= doc.getUserId() %>"></dhv:username>
			<% if (User.getRoleId()== new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD1"))
					|| User.getRoleId()== new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD2"))){%>
			(<%= doc.getUserIp() %>) 
			<%} %>
			</td> 
			<td bgcolor=<%=color %> >
			
			<a href="GestioneDocumenti.do?command=DownloadPDF&codDocumento=<%=doc.getIdHeader()%>&idDocumento=<%=doc.getIdDocumento() %>"><input type="button" value="DOWNLOAD"></input></a>
			
			</td> 
		</tr>
		<%} } else {%>
		<tr><td colspan="6"> Non sono stati generati documenti.</td></tr>
		
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
		<option value="<%=listaDocumenti.size()%>">Tutti</option>
	</select> / <%=listaDocumenti.size()%>
</div>
		
	<br>
<!-- p align="right">
<img style="text-decoration: none;" width="150" src="gestione_documenti/images/sdlogo.png" />
</p-->
		<!-- img src="images/icons/stock_zoom-page-16.gif" border="0"
			align="absmiddle" height="16" width="16" />
		<a href="#" onclick="openVerificaGlifo();" id="" target="_self">
		<span style="color:red">New:</span> Verifica Glifo</a-->

  <!-- dhv:pagedListControl object="AssetTicketInfo"/-->
</dhv:container>


</body>
</html>