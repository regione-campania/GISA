<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:useBean id="OrgDetails" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
 <jsp:useBean id="Partita" class="org.aspcfs.modules.macellazioninewopu.base.Partita" scope="request"/>
 <jsp:useBean id="ecList" class="java.util.Vector" scope="request"/>
  <%@page import="org.aspcfs.modules.macellazioninewopu.base.Art17ErrataCorrige"%>
  <jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../../initPage.jsp" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="css/moduli_screen.css">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script language="javascript">
function openPopupListaErrataCorrigeArt17(idPartita, altId, idErrataCorrige){
window.location='GestioneDocumenti.do?command=GeneraPDFMacelli&tipo=Macelli_17_ErrataCorrige&altId='+altId+'&idPartita='+idPartita+'&idErrataCorrige='+idErrataCorrige;
}
</script>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<center><strong><u>Moduli Errata Corrige generati per la partita numero <%=Partita.getCd_partita() %></u></strong></center>
<br/><br/>
<table width="100%" class="details">
<col width="5%"> <col width="10%"><col width="30%"> <col width="30%">
<tr><th> Num. </th> <th> Data </th> <th> Motivo </th> <th> Generato da </th> <th> Documento </th></tr> 
<%	if (ecList.size()>0){
		for (int i=0;i<ecList.size(); i++){
			Art17ErrataCorrige ec = (Art17ErrataCorrige) ecList.get(i);
				%>
<tr><td> <%=i+1 %>	</td>
<td> <%= toDateWithTimeasString(ec.getEntered()) %></td>
<td> <%= ec.getMotivo() %></td>
<td> <dhv:username id="<%= ec.getIdUtente() %>"></dhv:username></td>
<td> <input type="button" onClick="openPopupListaErrataCorrigeArt17('<%=ec.getIdPartita() %>', '<%=ec.getIdMacello() %>', '<%=ec.getId()%>')" value="RIGENERA PDF"/> </td>
</tr>			
				<% } } %>

</table>
</body>
</html>