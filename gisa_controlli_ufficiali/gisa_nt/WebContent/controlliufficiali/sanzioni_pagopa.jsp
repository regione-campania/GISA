<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<% if (org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("GESTIONE_PAGOPA")!=null && org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("GESTIONE_PAGOPA").equalsIgnoreCase("si")) { %>	  
		
<% if (PopolaCombo.isPrevistoPagoPA(TicketDetails.getId())){ %>
<br/>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
<tr><th colspan="2">PAGOPA</td> </tr>
<tr><td class="formLabel">AVVISI DI PAGAMENTO</td> <td> 
<dhv:permission name="gestione_pagopa-view">

<% if (TicketDetails.getListaAllegatiPV().size()>0) { %>
<input type="button" value="Gestione PagoPA" onClick="window.open('GestionePagoPa.do?command=View&origine=ProcessoVerbale&idSanzione=<%=TicketDetails.getId()%>','popupPagoPa<%=TicketDetails.getId() %>','height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes').focus()"/>
<% } else { %>
<font color="red">Attenzione. Su questa sanzione non &egrave; ancora stato caricato l'allegato Processo Verbale. La gestione PagoPA sar&agrave; disponibile dopo il caricamento.</font>
<% } %>
</dhv:permission> 
</td></tr>
</table>	  
<%} %>
		
<%} %>