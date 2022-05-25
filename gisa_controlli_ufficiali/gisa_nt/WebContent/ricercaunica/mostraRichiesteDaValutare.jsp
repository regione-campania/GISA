<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="org.aspcfs.modules.suap.base.Richiesta" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp" %>

<jsp:useBean id="ListaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterDialogRichiesteDaValidare.js"></script>

<jsp:useBean id="tipoRichieste" class="java.lang.String" scope="request"/>


<script>function ricercaPratica(){
	loadModalWindow();
	window.location.href="GisaSuapStab.do?command=SearchForm";
}
</script>

<% 
String selected = "davalidare";
String titolo = "DA VALIDARE";
String bottone = "APRI SCHEDA";
if (tipoRichieste!=null && tipoRichieste.equals("2")){
	selected = "archiviate";
	titolo = "NON ACCOLTE";	
	bottone="APRI";
}
if (tipoRichieste!=null && tipoRichieste.equals("3")){
	selected = "evase";
	titolo = "EVASE";
	bottone="APRI";
}
%>

<% 
String contesto2 = (String) application.getAttribute("SUFFISSO_TAB_ACCESSI");
	if (contesto2!=null && !contesto2.equals("") && contesto2.equals("_ext")){
			
	}else{ %>
	<%-- Trails --%>
		<table class="trails" cellspacing="0">
		<tr>
			<td>
				<a href="GestionePraticheAction.do?command=HomeGins">PRATICHE SUAP 2.0</a> >
				PRATICHE APICOLTURA
			</td>
		</tr>
		</table>
	<%-- Trails --%>
	<br>
<%}%>

<dhv:container name="suaprichiestevalidazione" selected="<%=selected %>"  object="" >



<!-- mostro tutte le richieste pendenti  con tabella in un pager-->
 <%
 if (request.getAttribute("lista_richieste")!=null)
 {
   ArrayList<Richiesta> richieste =(ArrayList<Richiesta>) request.getAttribute("lista_richieste"); 
 //ArrayList<Boolean> proseguiAttivi =(ArrayList<Boolean>) request.getAttribute("proseguibilita_richieste"); %>
 

<% 
String contesto = (String) application.getAttribute("SUFFISSO_TAB_ACCESSI");
	if (contesto!=null && !contesto.equals("") && contesto.equals("_ext")){
			
	}else{
%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<%-- <a href="GisaSuapStab.do?command=SearchForm"><dhv:label name="">Ricerca Pratiche SUAP</dhv:label></a> > --%>
<%-- <a href="GisaSuapStab.do?command=Default"><dhv:label name="">Pratiche SUAP</dhv:label></a> >  --%>
 <%=titolo %>
</td>
</tr>
</table>
<%}%>
 
<% if (tipoRichieste!=null && tipoRichieste.equals("3")){%>
<font color="blue" size="3"><b>Attenzione. In questa pagina sono elencate le 100 pratiche evase più recenti.

<br/>
<%} %>
 
 <center>
 <input type="button" class="yellowBigButton" style="width: 350px;" 
		onclick="sceltaOperazione('<%=contesto %>','SuapStab.do?command=Scelta&tipoInserimento=2&stato=7&fissa=api');" 
		value="INSERIMENTO PRATICA APICOLTURA" />
</center> 

 
<%
boolean filter = true;
if(richieste.size()>1000)
{
	filter=false;
}
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
			<option value="<%=richieste.size()%>">Tutti</option>
		</select> / <%=richieste.size()%>
	</div>




  	<table id ="tableRichiesteDaValidare" class="tableSorter">
  		<thead>
  			
  			
  			<tr class="tablesorter-headerRow" role="row">
		
		
				<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="CERCA PER ID PRATICA" class="<%=(filter) ? "filter-match tablesorter-header tablesorter-headerUnSorted" : "filter-false"%>"><div class="tablesorter-header-inner">ID PRATICA</div></th>
				<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="CERCA PER CODICE FISCALE IMPRESA" class="<%=(filter) ? "filter-match tablesorter-header tablesorter-headerUnSorted" : "filter-false"%>"><div class="tablesorter-header-inner">CODICE FISCALE IMPRESA</div></th>
				<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="CERCA PER PARTITA IVA" class="<%=(filter) ? "filter-match tablesorter-header tablesorter-headerUnSorted" : "filter-false"%>"><div class="tablesorter-header-inner">PARTITA IVA</div></th>
				<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="CERCA PER NOME IMPRESA"  class="<%=(filter) ? "filter-match tablesorter-header tablesorter-headerUnSorted" : "filter-false"%>"><div class="tablesorter-header-inner">NOME IMPRESA</div></th>
				<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="CERCA PER COMUNE"  class="<%=(filter) ? "filter-match tablesorter-header tablesorter-headerUnSorted" : "filter-false"%>"><div class="tablesorter-header-inner">COMUNE</div></th>
				<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="CERCA PER LINEA PRODUTTIVA"  class="<%=(filter) ? "filter-match tablesorter-header tablesorter-headerUnSorted" : "filter-false"%>"><div class="tablesorter-header-inner">LINEE PRODUTTIVE</div></th>
				
				
				<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="CERCA PER TIPO PRATICA" class="<%=(filter) ? "first-name filter-select" : "filter-false"%>"><div class="tablesorter-header-inner">TIPO PRATICA</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="CERCA PER DATA" class="filter-false"><div class="tablesorter-header-inner">DATA INSERIMENTO<BR> PRATICA</div></th>
			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO" class="filter-false"><div class="tablesorter-header-inner"></div></th>

				
				
				
			</tr>
	  		
  		</thead>
 		
 		<tbody aria-relevant="all" aria-live="polite">
 		
  		<%
  			if(richieste != null && richieste.size() > 0)
  			{
	  			for(int i=0;i<richieste.size(); i++)
	  			{
	  				Richiesta r = richieste.get(i);
	  				//boolean proseguiDaAttivare = proseguiAttivi.get(i);
	  			%>
	  			
	  			<tr>
	  				<td><%=r.getId() %> </td>
	  				<td><%=r.getCodiceFiscaleImpresa() %> </td>
	  				<td><%=r.getPartitaIva() %> </td>
	  				<td><%=r.getRagioneSociale() %> </td>
	  				<td><%=r.getComuneRichiedente() %> </td>
	  				
	  				<td> 
	  				<%=  toHtml(r.getDescrizione_linee_attivita())%>
	  				 </td>
	  				
	  				
	  				<td><%=r.getDescrTipoRichiesta() %> </td>

	  				<td><%=toDateasString(r.getEntered()) %></td>
	  				
					<td><input id="validaBtn" type="submit" value="<%=bottone %>" onClick="vaiAlDettaglio('<%=r.getAltId() %>');"/></td>
	  				
	  			</tr>
	  			
	  				
	  			<% 
	  			}
  			}
  			else
  			{%>
  				<tr><td colspan="11"> Non esistono richieste pendenti.</td></tr>
  			<%}%>
  		
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
			<option value="<%=richieste.size()%>">Tutti</option>
		</select> / <%=richieste.size()%>
	</div>
  	
  	<script>
  		function intercettaValidaBtn(idRichiesta,pIvaImpresa,codiceFiscaleImpresa,idTipoRichiesta)
  		{
  			
  			window.location.href = 'InterfValidazioneRichieste.do?command=PaginaPerRichiesta&idRichiesta='+idRichiesta+'&pIvaImpresa='+pIvaImpresa+"&codiceFiscaleImpresa="+codiceFiscaleImpresa+"&idTipoRichiesta="+idTipoRichiesta;

  		}
  		function vaiAlDettaglio(altId){
		loadModalWindow();
		//console.log("richiesta per la quale è attivato il bottone prosegui ? "+proseguiAttivo);
		window.location.href = 'GisaSuapStab.do?command=Details&altId='+altId;
  		}
  	</script>
  	<%}
 else
 {
	 out.print("NON SONO PRESENTI RICHIESTE");
 }
  	
  	%>
  	
  	</dhv:container>
  


<!--  PER GESTIONE INSERIMENTO -->
<jsp:include page="/suap/suap_scia_add_asl.jsp" />

<script>
function sceltaOperazione(contesto,url) //tiposcelta 1 per registrati, 2 per riconosciuti
{
	 if(contesto=='Suap')
		 {
		 goTo(url);
		 }
	 else
		 {
		 document.forms['scelta'].action=url;
		 
		 $( "#dialogSceltaComune" ).dialog('open');
		 }
}
</script>
<!--  PER GESTIONE INSERIMENTO -->
