<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.izsmibr.base.InvioMassivoIbr"%>
<%@page import="org.aspcfs.modules.izsmibr.base.DsESITOIBRIUS"%>
<%@ page import="java.util.*,org.aspcfs.utils.*"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../initPage.jsp"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />



<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterDialogRichiesteDaValidare.js"></script>

  <%! public static String fixStringa(String nome)
  {
	  String toRet = nome;
	  if (nome == null || nome.equals("null"))
		  return toRet;
	  toRet = nome.replaceAll("'", "");
	  toRet = toRet.replaceAll(" ", "_");
	  toRet = toRet.replaceAll("\\?","");
	
	  return toRet;
	  
  }%>
  
	
	 
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td width="100%"><a href="GestioneEsitoIbr.do?command=ToImportIbr"><dhv:label
			name="">INVIO ESITI IBR</dhv:label></a> > <dhv:label
			name="">LISTA INVII ESEGUITI</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>

<br>

  <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	

<dhv:container name="inviocuibr" selected="Lista Invii IBR" object="">


<table class="details" width="100%">
		 	<tr>
  				<th style="font-size:12px; text-align: center">Lista Invii Effettuati verso la bdn</th>
  			</tr>
  			
 </table>
 <%
 ArrayList<InvioMassivoIbr> rImport = ( ArrayList<InvioMassivoIbr> ) request.getAttribute("ListaInvii");
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
			<option value="<%=rImport.size()%>">Tutti</option>
		</select> / <%=rImport.size()%>
	</div>


  	<table id ="tableRichiesteDaValidare" class="tableSorter">
  		<thead>
  			
  			
  			<tr class="tablesorter-headerRow" role="row">
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">ID</div></th>
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">DATA</div></th>
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">CARICATO DA</div></th>
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">FILE INVIATO</div></th>
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">ESITo PDF</div></th>
  			<th aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRA PER ID RICHIESTA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">KO</div></th>
	</tr>
	</thead>
	
	
 		<tbody aria-relevant="all" aria-live="polite">
	
	
	<% 
	
		
		if ( rImport.size() > 0 ) {
			
				for ( int i=0; i< rImport.size(); i++ ) {
					
	%>
	
			<tr>
				<td align="right"><%= rImport.get(i).getId() %></td>
				<td align="right"><%=  toDateasStringWitTime(rImport.get(i).getData()) %></td>
				<td align="right"><dhv:username id="<%=rImport.get(i).getInviato_da() %>"/></td>
				<td align="right">
				<% if (rImport.get(i).getDocUplodato()!=null) {%>
				<a href="GestioneAllegatiInvii.do?command=DownloadPDF&codDocumento=<%=rImport.get(i).getDocUplodato().getIdHeader()%>&idDocumento=<%=(rImport.get(i).getDocUplodato()!=null )? rImport.get(i).getDocUplodato().getIdDocumento() :"" %>&tipoDocumento=<%=(rImport.get(i).getDocUplodato()!=null )? rImport.get(i).getDocUplodato().getTipoAllegato() : "" %>&nomeDocumento=<%=(rImport.get(i).getDocUplodato()!= null) ? fixStringa(rImport.get(i).getDocUplodato().getNomeClient() ): ""%>">
				<%= rImport.get(i).getDocUplodato().getNomeDocumento()%>	
				</a>
				<%}%>
				</td>
				<td align="right"><input type = "button" value="Download Esito"onclick="openRichiestaPDF_LogIBR(<%=rImport.get(i).getId() %>)"></td>
				<td align="right"><input type = "button" value="Download CSV OK/KO"onclick="location.href='GestioneEsitoIbr.do?command=EsportaCsv&idInvio=<%=rImport.get(i).getId() %>'"></td>
				
				
			</tr>
			<% } %>
		<% 
			
		} else { %>
		  <tr class="containerBody">
      			<td colspan="5">
        			<dhv:label name="">Nessun import eseguito.</dhv:label>
      			</td>
    	  </tr>
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
			<option value="30">40</option>
			<option value="40">40</option>
			<option value="<%=rImport.size()%>">Tutti</option>
		</select> / <%=rImport.size()%>
	</div>
  	
	

</dhv:container>
	