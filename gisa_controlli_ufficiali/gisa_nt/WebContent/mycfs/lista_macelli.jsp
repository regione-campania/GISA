<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.troubletickets.base.Ticket"%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.ricercaunica.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="macelliList" class="java.util.ArrayList" scope="request"/>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<center><b>Lista dai macelli associati a questa utenza</b></center>
<br/><br/>

<table cellpadding="8" cellspacing="0" border="0" style="width: 100%" class="pagedList">
<tr>
<th>RAGIONE SOCIALE</th>
<th>PARTITA IVA</th>
<th>ASL</th>
<th>APPROVAL NUMBER</th>
<th>SEDE OPERATIVA</th>
</tr>



<% for (int i = 0; i<macelliList.size(); i++) {
	RicercaOpu macello = (RicercaOpu) macelliList.get(i);
		%>

<tr>
<td><a href="StabilimentoSintesisAction.do?command=Details&altId=<%=macello.getRiferimentoId()%>"><%=macello.getRagioneSociale() %></a></td>
<td><%=macello.getPartitaIva() %></td>
<td><%=macello.getAsl() %></td>
<td><%=macello.getNumAut() %></td>
<td><%=macello.getIndirizzoSedeProduttiva() %></td>
</tr>


<% } %>
 
</table>





    

