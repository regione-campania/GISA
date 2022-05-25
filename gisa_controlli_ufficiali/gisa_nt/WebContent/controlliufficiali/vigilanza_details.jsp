<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>

<% 
String redirect = "";
redirect = TicketDetails.getURlDettaglio()+"Vigilanza.do?command=TicketDetails";
redirect+="&id="+TicketDetails.getId();

if (TicketDetails.getIdStabilimento()>0)
	redirect +="&stabId="+TicketDetails.getIdStabilimento()+"&idStabilimentoopu="+TicketDetails.getIdStabilimento();
if (TicketDetails.getOrgId()>0)	
	redirect +="&orgId="+TicketDetails.getOrgId();
if (TicketDetails.getIdApiario()>0)	
	redirect +="&stabId="+TicketDetails.getIdApiario()+"&idStabilimentoopu="+TicketDetails.getIdApiario();
if (TicketDetails.getAltId()>0)
	redirect +="&altId="+TicketDetails.getAltId();
%>

<% if (TicketDetails.getId()>0) { %>
	<script>
	loadModalWindow();
	window.location.href="<%=redirect%>";
	</script>
<% } else { %>

<font color="red">Controllo ufficiale non trovato.</font>
<% } %>