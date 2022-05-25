<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>



<%@page import="org.aspcfs.modules.dpat2019.base.Indicatore"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatIndicatore"%>
<%@page import="org.aspcfs.modules.dpat2019.*" %>



<script type="text/javascript" src="javascript/jquery.miny_1.7.2.js"></script>
<script   src="javascript/jquery-ui.js"></script>

<%@include file="../initPage.jsp" %>


<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />
 
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterDialogRichiesteDaValidare.js"></script>

<%@page import="java.util.ArrayList,org.servlet.*" %>
<jsp:useBean id="alIndicatori" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="piano" class="org.aspcfs.modules.dpat2019.base.PianoAttivita" scope="request" />
<jsp:useBean id="sezione" class="org.aspcfs.modules.dpat2019.base.Sezione" scope="request" />

<%--
<%

DpatIndicatore ind = (DpatIndicatore)request.getAttribute("DettaglioIndicatore");

ArrayList<DpatIndicatore> storia = ind.getStoriaIndicatore();
--%>

 
	<center>
	<font size="2px" style="color: red; font-weight: bold;" >
		versioni per INDICATORE codice univoco <u><%=((Indicatore)alIndicatori.get(0)).getCodiceAliasIndicatore() %></u>
	</font>
	</center>

  	<table id ="tableRichiesteDaValidare" class="tableSorter">
  		<thead>
  			
  			
  			<tr class="tablesorter-headerRow" role="row">
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">ANNO</div></th>
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">DESCRIZIONE</div></th>
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">CODICE UNIVOCO PIANO/ATTIVITA PADRE</div></th>
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">CODICE UNIVOCO</div></th>
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">ATTIVO AL</div></th>

	</tr>
	</thead>
	
		<tbody aria-relevant="all" aria-live="polite">
		
		<%-- <%
		for (DpatIndicatore indicatore : storia)
		{
			%>
			<tr>
			<td><%=indicatore.getAnnoRiferimento() %></td>
			<td><%=indicatore.getDescrizioneSezione() %></td>
			<td><%= toHtml(indicatore.getAliasPiano()) %></td>
			<td><%=indicatore.getDescrizioneAttivita() %></td>
			<td><%=indicatore.getDescription() %></td>
			<td><%=indicatore.getAlias() %></td>
<td><%=indicatore.getCodiceAlias() %></td>
			
			<td><%=(indicatore.getDataScadenza()!= null) ?indicatore.getDataScadenza()+"" : indicatore.getAnnoRiferimento()+"" %></td>
			
			</tr>
			
			<%
		}
		%> --%>
		
		<%
		for (Object bean : alIndicatori)
		{
			Indicatore beanInd = (Indicatore)bean;
			String descrSezMadre = beanInd.getDescrSezMadre();
			String descrPianoAttivitaPadre = beanInd.getDescrPianoAttivitaPadre();
			 
			
			%>
				 
			<tr>
			<td><%=beanInd.getAnno() %></td>
			<td><font color="orange"><%=descrSezMadre %></font><br>
				<font color="green"><%=descrPianoAttivitaPadre %></font><br>
				<font color="blue"><b>> </b><%=beanInd.getDescrizione() %></font><br>
			</td>
			<td><%= beanInd.getCodiceAliasPadre()%></td>
			<td><%= beanInd.getCodiceAliasIndicatore()%></td>
			<td><%=(beanInd.getScadenza()!= null) ? toDateasString(beanInd.getScadenza()) : beanInd.getAnno()+"" %></td>
			
			</tr>
			<%
		}
		%>
		</tbody>


</table>
	
	