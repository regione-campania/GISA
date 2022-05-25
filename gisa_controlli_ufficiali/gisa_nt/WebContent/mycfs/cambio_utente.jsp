<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.admin.base.Role"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">La Mia Home Page</dhv:label></a> >
<dhv:label name="">Cambio Utente</dhv:label>
</td>
</tr>
</table>
<%String errore = (String)request.getAttribute( "errore" ); %>

<%
	if(User.getRoleId()==Role.HD_2LIVELLO)
	{
%>
<form action="MyCFS.do?command=CambioUtenteConferma" method="post" >

<%=(errore == null) ? ("") : (errore) %>
<br/>
Username: <input type="text" name="username" />
<input type="submit" value="Procedi">
</form>
<%
	}
%>

<form action="Login.do?command=Logout" method="post" >
<%errore = (String)request.getAttribute( "errore" ); %>
<%=(errore == null) ? ("") : (errore) %>
<br/>
Codice fiscale: <input type="text" name="cf_spid" />
<input type="submit" value="Procedi">
</form>
