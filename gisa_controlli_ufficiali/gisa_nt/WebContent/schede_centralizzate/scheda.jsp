<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page pageEncoding="UTF-8" %>
    <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>

<jsp:useBean id="SchedaOperatore" class="org.aspcfs.modules.schedeCentralizzate.base.SchedaCentralizzata" scope="request"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>
<%@page import="org.servlet.ServletServiziScheda" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Scheda</title>
</head>
<link rel="stylesheet" type="text/css" media="all" documentale_url="" href="gestione_documenti/schede/schede_layout.css" />
<%@ include file="../gestione_documenti/schede/barcode.jsp" %>
<%--@ include file="../gestione_documenti/schede/json.jsp" --%>


<body>

<table width="100%"> 
<!-- <col width="20%"><col width="20%"><col width="20%"><col width="40%"> -->
<col width="33%"><col width="33%"><col width="33%">

<tr>
<td><div align="left"><img style="text-decoration: none;" width="80" height="80" documentale_url="" src="gestione_documenti/schede/images/regionecampania.jpg" /></div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

</td>
<td><center><b><label class="titolo"><%=SchedaOperatore.getTitolo() %></label></b></center>
</td>
<!-- <td><div class="boxQRDocumento"></div></td> -->
<td><div align="right"><img style="text-decoration: none;" height="80px" width="200px" documentale_url="" src="gestione_documenti/schede/images/<%=SchedaOperatore.getASL().toLowerCase() %>.jpg" /></div>
</td>
</tr>
</table>

<table cellpadding="5" style="border-collapse: collapse" width="100%" border="1px solid black">
<col width="33%">
<% 
LinkedHashMap<String,String[]> listaElementi = SchedaOperatore.getListaElementi();
for(Map.Entry<String, String[]> elemento : listaElementi.entrySet()){
	%>
	<!--  LABEL  -->
	<% if ( elemento.getValue()[1]!=null && elemento.getValue()[1].equals("capitolo")) { %>
	<tr><td colspan="4" class="grey"><b><%=elemento.getKey().toUpperCase() %></b></td></tr>
	<%} else if ( elemento.getValue()[0]!=null && !elemento.getValue()[0].replaceAll(" ", "").equals("") && !elemento.getValue()[0].equals("null")) { %>

<% if(elemento.getValue()[1]==null || !elemento.getValue()[1].equalsIgnoreCase("json")){ %>
<tr><td class="blue" ><%=elemento.getKey() %></td>
<%} %>

<!--  VALORE: SE ATTRIBUTO BARCODE MOSTRA COME BARCODE -->
<%if ( elemento.getValue()[1]!=null && elemento.getValue()[1].equalsIgnoreCase("barcode")) {%>
<td class="layout"><img src="<%=createBarcodeImage(elemento.getValue()[0])%>" /></td></tr>
<%} else if ( elemento.getValue()[1]!=null && elemento.getValue()[1].equalsIgnoreCase("json") ) {%>
<tr><td colspan="4" class="blue" ><%=elemento.getKey() %> </td></tr>
<tr><td colspan="4" class="layout"><%=/*createTableFromJson*/new ServletServiziScheda().generaTabellaJson(elemento.getValue()[0])%></td></tr>
<%} else { %>
<td class="layout"><%=  elemento.getValue()[0] %></td></tr>
<% } %>
<% } %> 
<% } %>
</table>

<% if (SchedaOperatore.isFirmaData()){ %>
<br/>
<table cellpadding="5" style="border-collapse: collapse" width="100%">
<tr><td>Data</td><td>Firma</td></tr>
<tr><td>_________________________________</td> <td>_________________________________</td> </tr>
</table>
<%} %>
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>
</body> 
</html>