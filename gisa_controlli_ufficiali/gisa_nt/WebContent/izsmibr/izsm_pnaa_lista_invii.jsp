<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.izsmibr.base.PrelievoPNAA"%>
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

<dhv:container name="inviocupnaa" selected="Lista Invii PNAA" object="">


<table class="details" width="100%">
		 	<tr>
  				<th style="font-size:12px; text-align: center">Lista Invii Effettuati verso sinvsa</th>
  			</tr>
  			
 </table>
 
 <%
 ArrayList<PrelievoPNAA> listaInvii = ( ArrayList<PrelievoPNAA> ) request.getAttribute("listaInvii");
 %>
 
<table width="100%" class="details">
<tr>
<th>idInvioMassivo</th><th>numeroScheda</th><th>motivoCodice</th><th>metodoCampionamentoCodice</th>
<th>dataPrelievo</th><th>numeroVerbale</th><th>cun</th>
<th>Esito</th>
</tr>
  
 <% for (int i = 0; i<listaInvii.size(); i++){
	 PrelievoPNAA invio = (PrelievoPNAA) listaInvii.get(i);%>
	 
	 <tr>
	 <td><%=invio.getIdImport() %></td>
	 <td><%=invio.getNumeroScheda() %></td>
	 <td><%=invio.getMotivoCodice() %></td>
	 <td><%=invio.getMetodoCampionamentoCodice() %></td>
	 <td><%=invio.getDataPrelievo() %></td>
	 <td><%=invio.getNumeroVerbale() %></td>
	 <td><%=invio.getCun() %></td>
	 <td><%=invio.getEsito() %></td>
	 </tr>
	<% } %>
 
 
 </table>
	

</dhv:container>
	