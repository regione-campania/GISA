<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.macellazioninew.utils.ConfigTipo"%>
<jsp:useBean id="Capo"				class="org.aspcfs.modules.macellazioninew.base.Capo"			scope="request" />

<%if (Capo.getStato_macellazione().equals("Incompleto: Presenti campioni senza esito.") && (Capo.isArticolo17() || Capo.isModello10())) {%>
<jsp:include page="include_capo_add_modify_esito.jsp" /><%}
else
{
%>
<%
ConfigTipo configTipo = (ConfigTipo)request.getSession().getAttribute("configTipo");
String fileToInclude = "include_capo_add_modify" + configTipo.getIdTipo() + ".jsp";
%>


<jsp:include page="<%=fileToInclude%>"/>

<%	
}
%>
