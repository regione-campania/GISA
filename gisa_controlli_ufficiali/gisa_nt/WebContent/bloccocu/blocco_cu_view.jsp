<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="EventoBlocco" class="org.aspcfs.modules.bloccocu.base.EventoBloccoCu" scope="request"/>
<%@ include file="../initPage.jsp" %>

</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
Blocco Controlli Ufficiali
</td>
</tr>
</table>
<%-- End Trails --%>


<%
if (EventoBlocco!= null && EventoBlocco.getId()>0)
{
	
%>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
   <th>Tipo Blocco</th>
    <th>Data Blocco</th>
    <th>Data Fine Blocco</th>
    <th>Motivo</th>
    <th>&nbsp;</th>
  </tr>
  <tr>
   <td><%= (EventoBlocco.getTipoBlocco().equalsIgnoreCase("in")) ? "BLOCCO CONTROLLI CON DATA INIZIO CONTROLLO COMPRESA NEL RANGE DI DATE" : "BLOCCO CONTROLLI CON DATA INIZIO CONTROLLO ESTERNA AL RANGE DI DATE"%></td>
    <td><%=toDateasString(EventoBlocco.getData_blocco() )%></td>
     <td><%=toDateasString(EventoBlocco.getData_sblocco())%></td>
    <td><%=EventoBlocco.getNote() %></td>
    <td><input type= "button" value = "Sblocca" onclick="location.href='BloccoCu.do?command=InsertSblocco&id=<%=EventoBlocco.getId() %>'"/></td>
  </tr>
</table>
<%}
else
{
%>
<input type = "button" onclick="location.href='BloccoCu.do?command=AddBlocco'" value = "AggiungiBlocco">

<%	
}
%>
