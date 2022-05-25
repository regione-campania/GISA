<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="Riunione" class="org.aspcfs.modules.meeting.base.Riunione" scope="request"/>
<jsp:useBean id="Rilascio" class="org.aspcfs.modules.meeting.base.Rilascio" scope="request"/>

<script>

<% if (Riunione!=null && Riunione.getId()>0) { %>
location.href='GestioneRiunioni.do?command=DettaglioRiunione&id=<%=Riunione.getId()%>';
<%} else if (Rilascio!=null && Rilascio.getId()>0) { %>
location.href='GestioneRiunioni.do?command=DettaglioRilascio&id=<%=Rilascio.getId()%>';
<% } %>


</script>
