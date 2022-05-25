<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="nomeMacello" class="java.lang.String" scope="request"/>
<jsp:useBean id="reg" class="org.aspcfs.modules.macellazionidocumenti.base.RegistroMacellazioni" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@page import="org.aspcfs.modules.accounts.base.OrganizationAddress"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<style>
@page { size: landscape !important;
 margin-top: 1cm !important;
 margin-bottom: 2cm !important;
  @bottom-center {
    content: counter(page) " su " counter(pages) !important;
    }
      @bottom-right {
      content: "Firma del veterinario _______________________________"  !important;
      }
}


 </style> 
 

<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheda</title>
</head>
<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="macellazionidocumenti/css/macelli_screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="macellazionidocumenti/css/macelli_print.css" />

<body>

<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

<br/><br/>

<table class="details" cellpadding="5" style="border-collapse: collapse;table-layout:fixed;" width="100%"> 
<col width="4%">
<col width="9%">
<col width="8%">
<col width="10%">
<col width="7%">
<col width="11%">
<col width="8%">
<col width="8%">
<col width="10%">
<col width="15%">
<col width="6%">
<col width="5%">

<thead>
<tr><td colspan="12"><div class="titolo2">REGISTRO MACELLAZIONI "<%=nomeMacello %>" del <%=toDateasString(reg.getData()) %></div></td></tr>
<tr>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Prg<br/>Bov</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Mod. 4</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Data<br/> arrivo</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Specie</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Cod. Allev.</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Matricola</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Data<br/> nascita</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Data<br/> Macellazione</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Esito<br/> Visita PM</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Destinatario<br/> Carni</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Dest.<br/> Carcassa</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Cat. rischio BSE</th>
</tr>
</thead>
<tbody>
<% 
LinkedHashMap<String,String[]> listaElementi = reg.getListaElementi();
for(Map.Entry<String, String[]> elemento : listaElementi.entrySet()){
	%>
<tr class="row<%=Integer.parseInt(elemento.getKey())%2%>">
<% for (int i = 0; i< elemento.getValue().length; i++) { %>
<td style="text-overflow: ellipsis; overflow: hidden;" align="center"><%=(elemento.getValue()[i]!=null) ? elemento.getValue()[i] : "" %></td>
<% } %></tr>
<% } %>
</tbody>
</table>

<!-- <br/><br/><br/><br/><br/> -->
<!--  <table> -->
<!--  <col width="33%"><col width="33%"> -->
<!--  <tr><td></td> -->
<!--  <td></td> -->
<!--  <td><i>Firma del veterinario</i> ___________________________________________</td> </tr> -->
<!-- </table> -->

<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>    
  

</body>
</html>