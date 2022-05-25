<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/ControlliUfficialiCampiAggiuntiviOpu.js"></script>


<!-- <tr id="Allev01"  style="display:none" class="campiAggiuntiviLinea"> -->
<!-- <td colspan="2" class="formLabel"> -->
<!-- 	<table cellpadding="4" cellspacing="0" width="100%" class="details"> -->
<!-- <tr id="preavviso"   class="containerBody"> -->
<!-- 		<td  class="formLabel"> -->
<!-- 			Effettuato Preavviso -->
<!-- 			</td> -->
<!-- 		<td> -->
<%-- 		<%if ("N".equalsIgnoreCase(TicketDetails.getFlag_preavviso()) ){%>Nessun Preavviso<%}  --%>
<%-- 		else if ("P".equalsIgnoreCase(TicketDetails.getFlag_preavviso()) ){%>Telefono<%}  --%>
<%-- 		else if ("T".equalsIgnoreCase(TicketDetails.getFlag_preavviso()) ){%>Telegramma<%} --%>
<%-- 		else if ("A".equalsIgnoreCase(TicketDetails.getFlag_preavviso()) ){%>Altro<%}  --%>
<%-- 		 else {  %>Nessun Preavviso<%} %> --%>
<!-- 		</td> -->
<!-- 		</tr> -->
		
		
<%-- 		<%if (TicketDetails.getData_preavviso_ba() != null) {%> --%>
<!-- 		<tr id="data_preavviso_ba_tr" class="containerBody"> -->
<!-- 		<td  class="formLabel"> -->
<!-- 			Data Preavviso -->
<!-- 			</td> -->
<!-- 		<td> -->
		
<%-- 		<%=toDateasString(TicketDetails.getData_preavviso_ba())%> --%>
		
<!-- 		</td> -->
<!-- 		</tr> -->
<%-- 		<%}%> --%>
<!-- </table></td></tr> -->


<script>
<% if (request.getAttribute("linea_attivita") != null) {
	ArrayList<LineeAttivita> linee = (ArrayList<LineeAttivita>) request.getAttribute("linea_attivita");
	for (LineeAttivita linea_di_attivita : linee) {
	int idLinea = linea_di_attivita.getId();%>
	controllaCampiAggiuntiviLinea('<%=idLinea%>', '<%=TicketDetails.getId()%>');
	<%} }%>
</script>
