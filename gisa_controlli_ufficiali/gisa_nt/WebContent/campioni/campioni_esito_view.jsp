<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 <jsp:useBean id="motiviAcqueNcList" class="java.util.ArrayList" scope="request"/>
<%@page import="org.aspcfs.modules.acquedirete.base.AcqueReteMotiviNc"%>
 
<% if (!TicketDetails.isEsitoCampioneChiuso() && !TicketDetails.isInformazioniLaboratorioChiuso()) { %>
<!-- 1: tutto modificabile -->
<%@ include file="/campioni/campioni_esito_view_1.jsp" %>
<%@ include file="/campioni/ritorno_esito_da_laboratorio_view.jsp" %>
<%} 
else if (TicketDetails.isEsitoCampioneChiuso() && !TicketDetails.isInformazioniLaboratorioChiuso()) { %>
<!-- 2: modificabile solo info laboratorio -->
<%@ include file="/campioni/campioni_esito_view_2.jsp" %>
<%@ include file="/campioni/ritorno_esito_da_laboratorio_view.jsp" %>
<%} else { %>
 <!-- 3: solo lettura -->
 <%@ include file="/campioni/campioni_esito_view_3.jsp" %>
<%} %>

