<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.sql.Connection"%>
<%@page import="com.darkhorseventures.database.ConnectionElement"%>
<%@page import="com.darkhorseventures.database.ConnectionPool"%>
<%@page import="org.aspcfs.modules.macellazioninewopu.utils.ConfigTipo"%>
<jsp:useBean id="Partita"				class="org.aspcfs.modules.macellazioninewopu.base.Partita"			scope="request" />


<%
	ConnectionElement ce = (ConnectionElement) request.getSession().getAttribute("ConnectionElement");
	ConnectionPool sqlDriver = (ConnectionPool) request.getServletContext().getAttribute("ConnectionPool");
	Connection db = sqlDriver.getConnection(ce,null);

	ConfigTipo configTipo = (ConfigTipo)request.getSession().getAttribute("configTipo");
	if (Partita.getStato_macellazione().equals("Incompleto: Presenti campioni senza esito.") && (Partita.getStampatoArt17(db, configTipo).equals("SI") || Partita.isModello10())) 
	{
%>
		<jsp:include page="include_capo_add_modify_esito.jsp" />
<%	}
	else
	{
		String fileToInclude = "include_capo_add_modify" + configTipo.getIdTipo() + ".jsp";
%>

<jsp:include page="<%=fileToInclude%>"/>

<%	
}

   if (db != null) 
	   sqlDriver.free(db);
   db = null;
%>  
