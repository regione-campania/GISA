<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>



<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<jsp:useBean id="listaRichieste" class="java.util.LinkedHashMap" scope="request"/>

<jsp:useBean id="limit" class="java.lang.String" scope="request"/>

<jsp:useBean id="msg" class="java.lang.String" scope="request"/>
<jsp:useBean id="msgValidazione" class="java.lang.String" scope="request"/>
<jsp:useBean id="msgScartatiPresenti" class="java.lang.String" scope="request"/>
<jsp:useBean id="msgScartatiApprovalTroppoLungo" class="java.lang.String" scope="request"/>


<jsp:useBean id="idImport" class="java.lang.String" scope="request"/>


<%@page import="org.aspcfs.modules.sintesis.base.StabilimentoSintesisImport"%>

<%@ include file="../initPage.jsp" %>

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
	src="javascript/jquerypluginTableSorter/tableJqueryFilterPraticheSintesis.js"></script>
<script src="javascript/jquery.searchable-1.0.0.min.js"></script>


  <link rel="stylesheet" type="text/css" media="screen" href="css/jquery.ui.combogrid.css"/>
  <script type="text/javascript" src="javascript/jquery.ui.combogrid.js"></script>
<style>
<!--

.combogrid{
	/*max-width: 800px;
	min-width: 500px;*/
	font-size: 0.8em !important;
	width: 25%;
	height: 50%;
	overflow: scroll;
	
	
}
.cg-colHeader-label {
	padding: 10;
	margin:10;
	float:center;
}
.cg-DivItem {
	
	float:center;
	font-size: 0.8em;
	overflow: visible;
	height: auto;
}
-->
</style>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script>function refreshLista(form){
	loadModalWindow();
	form.submit();
}</script>

<script>
function mostraNascondi(campo){
	var table = document.getElementById(campo);
	if (table.style.display=="block")
		table.style.display="none";
	else
		table.style.display="none";
}

</script>

		 <script>
  function processaDatiIgnora(form){

	  if (confirm('ATTENZIONE: ignorare gli errori sui flussi e processare tutta la coda?')){
	   	form.ignoraFlussoStati.value="ok";
	   	loadModalWindow();
   		form.submit();
   }
   else
   	return false;
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
	  toRet =giorno+"/"+mese+"/"+anno;
	  return toRet;
	  
  }%>

    <br>
   
  <dhv:container name="sintesisimport" selected="Pratiche" object="">
  
  
  
  <% if (msg!=null && !msg.equals("null") && !msg.equals("")){ %>
  <table class="details" width="100%"><tr><th>ESITO IMPORT</th></tr>
  <tr><td>
  <font color="green"> <%=msg %></font>
  </td></tr></table>
  <br/><br/>
  <%} %>

 <% if ((msgScartatiPresenti!=null && !msgScartatiPresenti.equals("null")  && !msgScartatiPresenti.equals("")) || (msgScartatiApprovalTroppoLungo!=null && !msgScartatiApprovalTroppoLungo.equals("null")  && !msgScartatiApprovalTroppoLungo.equals(""))){ %>
  <table class="details" id="duplicati" width="100%"><tr><th>SCARTATI <input type="button" value="X" onClick="mostraNascondi('duplicati')" /> </th></tr>
  <tr><td>
  <font color="maroon"> <%=msgScartatiApprovalTroppoLungo %></font>
  <font color="orange"> <%=msgScartatiPresenti %></font>
  </td></tr></table>
  <br/><br/>
  <%} %>
    
  <% if (msgValidazione!=null && !msgValidazione.equals("null")  && !msgValidazione.equals("")){ %>
  <table class="details" width="100%"><tr><th>ESITO PROCESS</th></tr>
  <tr><td><%=msgValidazione %></td></tr>
  <% if (idImport!=null && !idImport.equals("")){ %>
  <tr><td>
  <br/>
  <form id = "addAccount" name="addAccount" action="StabilimentoSintesisAction.do?command=ProcessaCoda&auto-populate=true" method="post">
  <input type="hidden" name="idImport"	id="idImport" value="<%=idImport%>">
  <input type="button" value="IGNORA GLI ERRORI SUL FLUSSO STATI E PROCESSA TUTTI" onClick="processaDatiIgnora(this.form)"/>
  <input type="hidden" id="ignoraFlussoStati" name="ignoraFlussoStati" value=""/>
  </form></td></tr>
  <%} %>
  </table>
  <br/><br/>
 <% } %>
  
  <center><b>Lista pratiche</b></center>
  		
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
		<option value="50">50</option>
	</select>
</div>
		<table class="tablesorter" id = "tablelistapratichesintesis">

			<thead>
				<tr class="tablesorter-headerRow" role="row">
				
				<th
						aria-label="Data Documento Sintesis: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						data-placeholder="FILTRO PER DATA DOCUMENTO SINTESIS"
						class="sorter-shortDate dateFormat-ddmmyyyy"><div
							class="tablesorter-header-inner">Data Documento SINTESIS</div></th>

					<th
						aria-label="Stato sede operativa: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						data-placeholder="FILTRO PER STATO SEDE OPERATIVA"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">Stato sede operativa</div></th>

					<th
						aria-label="Approval number ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER APPROVAL NUMBER"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">Approval number</th>
					<th
						aria-label="Denominazione sede operativa ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER DENOMINAZIONE SEDE OPERATIVA"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">Denominazione sede operativa</th>
					<th
						aria-label="Partita IVA ( filter-match ): No sort applied, activate to apply an ascending sort"
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
							class="tablesorter-header-inner">INDIRIZZO</th>
					<th
						aria-label="Attivita ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER ATTIVITA"
						class="first-name filter-select"><div
							class="tablesorter-header-inner">ATTIVITA</th>
					
					<th
						aria-label="Descrizione attivita ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER DESCRIZIONE ATTIVITA"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">DESCRIZIONE ATTIVITA</th>
					
					<th
						aria-label="Stato ( filter-match ): No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="1"
						data-placeholder="FILTRO PER STATO"
						class="filter-match tablesorter-header tablesorter-headerUnSorted"><div
							class="tablesorter-header-inner">STATO</th>
					
					<th
						aria-label="Caricamento in anagrafica: No sort applied, activate to apply an ascending sort"
						aria-sort="none" style="-moz-user-select: none;" unselectable="on"
						aria-controls="table" aria-disabled="false" role="columnheader"
						scope="col" tabindex="0" data-column="0"
						class="filter-false tablesorter-header"><div
							class="tablesorter-header-inner">CARICAMENTO IN ANAGRAFICA</div>
					</th>
				</tr>
			</thead>
  		

		<tbody aria-relevant="all" aria-live="polite">
			<%
			
			LinkedHashMap<String, ArrayList<StabilimentoSintesisImport>> listaRichiesteHash = listaRichieste; 
			int j = 0;
			for ( String key : listaRichiesteHash.keySet() ) {
				
			    ArrayList<StabilimentoSintesisImport> listaRichiesteAggreg = (ArrayList<StabilimentoSintesisImport>) listaRichiesteHash.get(key);
			    j++;
				for (int i=0;i<listaRichiesteAggreg.size(); i++){
					StabilimentoSintesisImport stab = (StabilimentoSintesisImport) listaRichiesteAggreg.get(i);
					
			  %>
			  	<tr class="row<%=j%2%>">
			  	
			  	
			<td ><%= fixData(stab.getDataSintesis().toString())%></td> 
			<td ><%= stab.getStatoSedeOperativa()%></td> 
			<td ><%=stab.getApprovalNumber() %></td> 
			<td > <%=stab.getDenominazioneSedeOperativa() %></td> 
			<td > <%=stab.getPartitaIva() %> </td> 
			<td> <%=stab.getIndirizzo() %> <%=stab.getComune() %> <%=stab.getProvincia() %></td> 
			
			  	
			  	
			<td><%=stab.getAttivita() %> </td> 
			<td><%=stab.getDescrizioneSezione() %> </td> 
			<td><%=stab.getStatoAttivita() %> </td> 
			
			<td> 
			
			<% if (stab.getStatoImport()== StabilimentoSintesisImport.IMPORT_DA_VALIDARE){ %>
			<dhv:permission name="sintesis-add">
				<a href="StabilimentoSintesisAction.do?command=DettaglioCompletaRichiesta&id=<%=stab.getId()%>" title="Elaborazione e completamento del dato SINTESIS per caricamento in anagrafica GISA" style="text-decoration:none"><font size ="3px">[» »]</font></a>
				</dhv:permission>
				<%} else {  %>
				
				<% if (stab.getStatoImport()== StabilimentoSintesisImport.IMPORT_RIFIUTATO){ %>
				RIFIUTATO<br/>
				<%} else {%>
				PROCESSATO<br/>
				<%} %>
				
				<b>Elaborato da: </b><br/> <dhv:username id="<%= stab.getIdUtenteProcess() %>" /><br/>
				<b>in data: </b> <%=toDateasString(stab.getDataProcess()) %>
			<%} %>
			</td>
			
			</tr> 
			  
			  <%
			}	
			}
			%>
	 </tbody>
	</table>
	
<br/><br/>
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
		<option value="50">50</option>
	</select>
</div>

<form id="sel" name="sel" action="StabilimentoSintesisAction.do?command=ListaRichiesteAggregate&auto-populate=true" method="post">
<!-- <div align="right"> -->


<!-- <select id="limit" name="limit" onChange="refreshLista(this.form)")> -->
<%-- <option value="10" <%=limit.equals("10") ? "selected" : "" %>>10</option> --%>
<%-- <option value="50" <%=limit.equals("50") ? "selected" : "" %>>50</option> --%>
<%-- <option value="100" <%=limit.equals("100") ? "selected" : "" %>>100</option> --%>
<%-- <option value="-1" <%=limit.equals("-1") ? "selected" : "" %>>Tutte</option> --%>
<!-- </select>   -->
<!-- </div> -->
</form>
	</dhv:container>

<br/><br/>

</body>
</html>