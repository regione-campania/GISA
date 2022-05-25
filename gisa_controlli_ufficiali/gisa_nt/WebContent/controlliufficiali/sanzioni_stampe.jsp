<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<% if (org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("GESTIONE_PAGOPA")!=null && org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("GESTIONE_PAGOPA").equalsIgnoreCase("si")) { %>	  
<script>
function openPopupStampa(tipo, idsanzione) {
	 window.open('GestionePagoPa.do?command=StampaSanzione&tipo='+tipo+'&idSanzione='+idsanzione,'popupSelectPA',
     'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>

<div align="center"><a href="#" onClick="openPopupStampa('PV', '<%=TicketDetails.getId()%>')">Stampa per Processo Verbale</a></div><br/>
<div align="center"><a href="#" onClick="openPopupStampa('NO', '<%=TicketDetails.getId()%>')">Stampa per Numero Ordinanza</a></div><br/>
<% } %>