<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.HashMap"%>

<%
HashMap<String, HttpSession> utenti = null;
utenti = (HashMap<String, HttpSession>)request.getSession().getServletContext().getAttribute("utenti");
%>

<html><body>
<a href="checkUtenti.jsp" target=_blank>
<script>var d=new Date(); document.write('<b> Utenti alle ' + d.getHours()+'-'+ d.getMinutes() +'-'+  d.getSeconds() + '</b> :  ')</script>
</a>
<span style="background-color:yellow;font:small-caption;font-size:130%;font-weight:bold">
&nbsp; <%= utenti != null ? utenti.size() : "Nessun utente"%>  &nbsp;
</span>
</body></html>
