<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="esitoVariazioneStatoLinee" class="java.lang.String" scope="request"/>
<jsp:useBean id="id" class="java.lang.String" scope="request"/>


<script>
loadModalWindow();
</script>

<% if (esitoVariazioneStatoLinee!=null && !esitoVariazioneStatoLinee.equals("")){ %>
<script>
alert('<%=esitoVariazioneStatoLinee%>');
</script>
<%} %>


<% String url = "";
url = TicketDetails.getURlDettaglioanagrafica()+".do?command=Details";

if (TicketDetails.getTipologia_operatore()==1)
	url+="&orgId="+id;
else if (TicketDetails.getTipologia_operatore()==999)
	url+="&stabId="+id;
%>

<script>
window.location.href = "<%=url%>";
</script>


