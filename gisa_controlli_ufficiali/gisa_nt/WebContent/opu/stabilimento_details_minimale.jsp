<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>

<link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>		
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>

<%@page import="java.sql.Timestamp"%>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp"%>



<%
String param = "stabId="+StabilimentoDettaglio.getIdStabilimento()+"&opId=" + StabilimentoDettaglio.getIdOperatore();
%>

<%
String nomeContainer = "suapminimale";

%>

<dhv:container name="<%=nomeContainer %>"  selected="Scheda" object="Operatore" param="<%=param%>"  hideContainer="false">

<jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=StabilimentoDettaglio.getIdStabilimento() %>" />
       <jsp:param name="objectIdName" value="stab_id" />
     <jsp:param name="tipo_dettaglio" value="15" />
     </jsp:include>

</dhv:container>

