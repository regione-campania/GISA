<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
<% if((OrgDetails.getTipoDest()!=null)&& (OrgDetails.getTipoDest().equals("Autoveicolo"))){
        	
    %>
   <%-- img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
   <input type="button" title="Stampa scheda" value="Stampa scheda"	onClick="javascript:window.location.href='SchedaPrint.do?command=PrintReport&file=account_attivitaMobili.xml&id=<%= OrgDetails.getId() %>&addressid=<%= addressid%>&addressid2=<%= addressid2%>&addressid3=<%= addressid3%>';">
   
   <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
   <input type="button" title="Stampa scheda" value="Visualizza scheda PDF"	onClick="javascript:window.location.href='SchedaPrint.do?command=StampaScheda&file=account_attivitaMobili.xml&id=<%= OrgDetails.getId() %>&addressid=<%= addressid%>&addressid2=<%= addressid2%>&addressid3=<%= addressid3%>';"--%>
   
    <%-- img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
   <input type="button" title="Stampa scheda" value="Stampa scheda"	onClick="openRichiestaPDF('<%= OrgDetails.getId() %>', '<%= addressid%>', '<%= addressid2%>', '<%= addressid3%>', 'account_attivitaMobili.xml', 'SchedaImpresa');"--%>
   
   <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
   <input type="button" title="Stampa Scheda" value="Stampa Scheda"	onClick="openRichiestaPDF2('<%= OrgDetails.getId() %>', '<%= addressid%>', '<%= addressid2%>', '<%= addressid3%>','12');">
   
 <%}else{
 		%>
  <%--img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
  <input type="button" title="Stampa scheda" value="Stampa scheda"	onClick="javascript:window.location.href='SchedaPrint.do?command=PrintReport&file=account.xml&id=<%= OrgDetails.getId() %>&addressid=<%= addressid%>&addressid2=<%= addressid2%>&addressid3=<%= addressid3%>';">

<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
   <input type="button" title="Stampa scheda" value="Visualizza scheda PDF"	onClick="javascript:window.location.href='SchedaPrint.do?command=StampaScheda&file=account.xml&id=<%= OrgDetails.getId() %>&addressid=<%= addressid%>&addressid2=<%= addressid2%>&addressid3=<%= addressid3%>';"--%>
   
  <%-- <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
   <input type="button" title="Stampa scheda" value="Stampa scheda"	onClick="openRichiestaPDF('<%= OrgDetails.getId()', '<%= addressid%>', '<%= addressid2%>', '<%= addressid3%>', 'account.xml', 'SchedaImpresa');"> --%>
   
    <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
   <input type="button" title="Stampa Scheda" value="Stampa Scheda"	onClick="openRichiestaPDF2('<%= OrgDetails.getId() %>', '<%= addressid%>', '<%= addressid2%>', '<%= addressid3%>','1');">
   

<%} %>

	
	
 

