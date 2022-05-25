<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<h4 class="titolopagina">
     Dettaglio Operazione Utente
</h4> 

<table class="tabella" style="width: 99%">
	<tr class='even'><td>Id</td><td>${op.id}</td></tr>
	<tr class='odd'><td>User-Id</td><td>${op.user_id}</td></tr>
	<tr class='even'><td>Username</td><td>${op.username}</td></tr>
	<tr class='odd'><td>IP</td><td>${op.ip}</td></tr>
	<tr class='even'><td>Data</td><td>${op.data}</td></tr>
	<tr class='odd'><td>Browser</td><td>${op.userBrowser}</td></tr>
	<tr class='even'><td>URL</td><td>${op.url}</td></tr>
	<tr class='odd'><td>Parametri</td><td>${op.parameter}</td></tr>
	<tr class='even'><td>ARCHIVIAZIONE<br>AUTOMATICA</td><td>${op.automatico}</td></tr>
	<tr class='odd'><td>Action</td><td>${op.action}</td></tr>
</table>
