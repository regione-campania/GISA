<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.db.ApplicationProperties"%>
<script src="<%=ApplicationProperties.getProperty("SITO_GISA")%>/js/GisaSpid.js"></script>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<LINK REL="SHORTCUT ICON" HREF="images/logo.png">
<img id="logoregione" align="right" src="images/logo.png"/>
<c:if test="${utente!=null}" >
<div style="clear: both;" align="right">
<c:out value="${utente.cognome}"/>,<c:out value="${utente.nome}"/> (<c:out value="${utente.username}"/>)
<a href="javascript: GisaSpid.logoutSpid('login.Logout.us')">[Logout]</a>
</div>
</c:if>