<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="url" class="java.lang.String" scope="request" />

<%
//Faccio questo perche se il protocollo non e https, nella url non 
//compare e il browser blocco per questione di sicurezza la navigazione verso un protocollo sconosciuto
String protocollo = "";
if(url.indexOf("http")<0)
	protocollo = "http://";
%>
<script type="text/javascript">
window.location.href='<%=protocollo+url%>';
</script>