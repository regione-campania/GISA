<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="nomeMacello" class="java.lang.String" scope="request"/>
<jsp:useBean id="comuneMacello" class="java.lang.String" scope="request"/>
<jsp:useBean id="approvalNumber" class="java.lang.String" scope="request"/>
<jsp:useBean id="aslMacello" class="java.lang.String" scope="request"/>
<jsp:useBean id="art17" class="org.aspcfs.modules.macellazionidocumenti.base.Articolo17" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
  <jsp:useBean id="nomeEsercente" class="java.lang.String" scope="request"/>
  <jsp:useBean id="indirizzoEsercente" class="java.lang.String" scope="request"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<style>
@page { 
 margin: 1cm !important;
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

<table width="100%">
<col width="33%"><col width="33%"><col width="20%">
<tr>
<td><img style="text-decoration: none;" height="80px" max-width="130px" documentale_url="" src="gestione_documenti/schede/images/<%= SiteList.getSelectedValue(aslMacello).toLowerCase() %>.jpg" /></td>
<td>
<div class="titolo">
<center>Regione Campania<br/>
Azienda Sanitaria Locale <%= SiteList.getSelectedValue(aslMacello) %><br/>
Servizio Veterinario<br/>
<i>ISPEZIONI DELLE CARNI</i><br/>
<div class="piccolo">(Art 17 R.D. 20/12/1928, N. 3298)</div>
</center>
</div>
</td>
<td>&nbsp;</td>
<td>
<div class="image">
<img height="80px" max-width="130px" documentale_url="" src="macellazionidocumenti/css/ovale.png" alt="" /> 
<div class="testoInImage">
<center>IT <br/>
<%=approvalNumber %><br/>
CE</center>
</div>
</div>

</td>
</tr>
</table>


<div class="titolo" align="left">
<div class="rettangolo" align="left">
N° &nbsp;&nbsp;&nbsp; <%= art17.getProgressivoMacellazione() + "/" + art17.getAnno() + "/" + approvalNumber%> 
</div>
MACELLO: <%=nomeMacello %></div>
<div class="sottotitolo" align="left">
Comune di <%=comuneMacello %><br/>
Data della macellazione: <%=toDateasString(art17.getData()) %><br/>
PROPRIETARIO DEGLI ANIMALI : <%=(art17.getProprietario()!=null) ?  art17.getProprietario() : "" %> <br/>
ESERCENTE <%= nomeEsercente%> <%=indirizzoEsercente %>
</div>
<br/>
<table class="details" cellpadding="5" style="border-collapse: collapse;table-layout:fixed;" width="100%">
<col width="10%">
<col width="10%">
<col width="10%">
<col width="10%">
<col width="10%">
<col width="10%">
<col width="10%">
<col width="10%">
<col width="10%">
<col width="10%">

<tr>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Prog.</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Partita</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Seduta</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Categoria<br/>Macellazione</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Ovini/<br/>Cinghiali<br/>macellati</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Caprini/<br/>Suini<br/>macellati</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Categoria</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Mod 4</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Veterinari</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Esito visita</th>
</tr>
<% 
LinkedHashMap<String,String[]> listaElementi = art17.getListaElementi();
for(Map.Entry<String, String[]> elemento : listaElementi.entrySet()){
	%>
<tr class="row<%=Integer.parseInt(elemento.getKey())%2%>">
<% for (int i = 0; i< elemento.getValue().length; i++) { %>
<td style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;"><%=(elemento.getValue()[i]!=null) ? elemento.getValue()[i] : "" %></td>
<% } %>
</tr>
<% } %>

</table>


<div class="fondo">
       <table width="100%">
	   <col width="20%"><col width="50%">
       <tr>
       <td><div align="left"><div class="bollo"><br/>BOLLO</div></div></td>
	   <td align="left">Materiale specifico a rischio distrutto come per legge</td>
       <td><div align="right"><i>IL VETERINARIO UFFICIALE<br/>
		DELL'ASL <%= SiteList.getSelectedValue(aslMacello) %></i></div></td>
       </tr>       
       </table>
    </div>
    
    <br/><br/>
  <%--div style="page-break-before:always">&nbsp; </div--%>  
  
 <div align="left">ALTRE SPECIE *</div>
<table class="details" cellpadding="5" style="border-collapse: collapse;table-layout:fixed;" width="100%">
<tr>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Prog.</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Partita</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">N. capi ovini</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">N. capi caprini</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Categoria</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Rif Mod 4</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Veterinari</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Esito visita</th>
</tr>
<% for (int i =0; i<6; i++){
	%>
<tr>
<% for (int j = 0; j< 8; j++) { %>
<td style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">&nbsp;</td>
<% } %>
</tr>
<% } %>

</table>

<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>

</body>
</html>