<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.contacts.base.Contact"%>
<%@page import="org.aspcfs.modules.mu.operazioni.base.VisitaPMSemplificata"%>
<%@page import="sun.reflect.generics.reflectiveObjects.NotImplementedException"%>
<%@page import="org.aspcfs.modules.mu.operazioni.base.VisitaPM"%>
<%@page import="org.aspcfs.modules.mu.operazioni.base.Macellazione"%>
<%@page import="org.aspcfs.modules.mu.base.CapoUnivoco"%>
<%@page import="org.aspcfs.modules.mu.base.CapoUnivocoList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="macello" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request"/>
<jsp:useBean id="esitiPm" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="art17" class="org.aspcfs.modules.mu.base.Articolo17" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
  <jsp:useBean id="esercente" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request"/>
    <%@page import="org.aspcfs.modules.accounts.base.OrganizationAddress"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<style>
@page { 
 margin-bottom: 1cm !important;
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
<%-- <div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div> --%>

<table width="100%">
<col width="33%"><col width="33%"><col width="20%">
<tr>
<td><img style="text-decoration: none;" height="80px" max-width="130px" documentale_url="" src="gestione_documenti/schede/images/<%= SiteList.getSelectedValue(macello.getSiteId()).toLowerCase() %>.jpg" /></td>
<td>
<div class="titolo">
<center>Regione Campania<br/>
Azienda Sanitaria Locale <%= SiteList.getSelectedValue(macello.getSiteId()) %><br/>
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
<%=macello.getApprovalNumber() %><br/>
CE</center>
</div>
</div>

</td>
</tr>
</table>

<%String indirizzoMacello="";
 Iterator iaddressM = macello.getAddressList().iterator();
    while (iaddressM.hasNext()) {
      OrganizationAddress thisAddress = (OrganizationAddress)iaddressM.next();
      if (thisAddress.getType()==5)
		indirizzoMacello = thisAddress.getCity() + " ("+ thisAddress.getState()+")";
    }
  
%>

<%String indirizzoEsercente="";
 Iterator iaddress = esercente.getAddressList().iterator();
 while (iaddress.hasNext()) {
      OrganizationAddress thisAddress = (OrganizationAddress)iaddress.next();
      if (thisAddress.getType()==5)
      		indirizzoEsercente = " - "+ thisAddress.getStreetAddressLine1()+", "+  thisAddress.getCity() + " ("+ thisAddress.getState()+")";
    }
  
%>

<div class="titolo" align="left">
<div class="rettangolo" align="left">
N° &nbsp;&nbsp;&nbsp; <%= art17.getProgressivo() + "/" + art17.getAnno() + "/" + macello.getApprovalNumber()%> 
</div>
MACELLO: <%=macello.getName() %></div>
<div class="sottotitolo" align="left">
Comune di <%=indirizzoMacello %><br/>
Data della macellazione: <%=toDateasString(art17.getDataCreazione())%><br/>
ESERCENTE <%=(esercente.getName()!=null) ? esercente.getName() : art17.getNomeEsercente() %> <%=indirizzoEsercente %>
</div>
<br/>
<table class="details" cellpadding="5" style="border-collapse: collapse;table-layout:fixed;" width="100%">
<col width="15%">
<col width="8%">
<col width="10%">
<col width="11%">
<col width="10%">
<col width="6%">
<col width="8%">
<col width="16%">
<col width="16%">
<thead><tr> 
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Auricolare</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Mezzene</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Data di nascita</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Specie</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Categoria</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Sesso</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Rif Mod 4</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Veterinari</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Esito visita</th>
</tr>
</thead>
<tbody>
<% 
ArrayList<CapoUnivoco> listaAnimali = art17.getListaCapi();
Iterator i = listaAnimali.iterator();
VisitaPM visitaPm = null;
VisitaPMSemplificata visitaPmS = null;

while (i.hasNext()){
	
	CapoUnivoco thisCapo = (CapoUnivoco) i.next();
	Macellazione dettaglioMacellazione = thisCapo.getDettagliMacellazione();
	
	try{
	 visitaPm = dettaglioMacellazione.getVisitaPm();
	
	}catch(NotImplementedException e){
		visitaPmS = dettaglioMacellazione.getVp();
		 
	}
	%>
	
<td style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;"><%=(thisCapo.getMatricola() !=null) ? thisCapo.getMatricola() : "" %> </td>	
<td style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">MEZZENE</td>	
<td style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;"><%=toDateasString(thisCapo.getDataNascita()) %></td>	
<td style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;"><%=(thisCapo.getSpecieCapoNome())%></td>	
<td style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;"><%=(thisCapo.getCategoriaBovina())%></td>	
<td style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;"><%=(thisCapo.getSesso())%></td>
<td style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;"><%=(thisCapo.getPartita().getMod4())%></td>	
<td style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">


	<%
		HashMap<String, ArrayList<Contact>> listaVeterinari = (HashMap<String, ArrayList<Contact>>) request
			.getAttribute("listaVeterinari");
	%>
	<dhv:evaluate if="<%=(visitaPm != null && visitaPm.getIdVeterinario1Pm() > 0)%>">
		 <%
				for (String gruppo : listaVeterinari.keySet()) {
			%> <%
 	for (Contact vet : listaVeterinari.get(gruppo)) {
 				if (vet.getUserId() == visitaPm.getIdVeterinario1Pm()){
 %> <%=vet.getNameLast()%> <%
 	break;}}
 %> <%
 	}
 %>
	</dhv:evaluate>

	<dhv:evaluate if="<%=(visitaPm != null && visitaPm.getIdVeterinario2Pm() > 0)%>">
		> <%
				for (String gruppo : listaVeterinari.keySet()) {
			%> <%
 	for (Contact vet : listaVeterinari.get(gruppo)) {
 				if (vet.getUserId() == visitaPm.getIdVeterinario2Pm()){
 %> <%=vet.getNameLast()%> <%
 	break;}}
 %> <%
 	}
 %>

	</dhv:evaluate>

	<dhv:evaluate if="<%=(visitaPm != null && visitaPm.getIdVeterinario3Pm() > 0)%>">
		> <%
				for (String gruppo : listaVeterinari.keySet()) {
			%> <%
 	for (Contact vet : listaVeterinari.get(gruppo)) {
 				if (vet.getUserId() == visitaPm.getIdVeterinario3Pm()){
 %> <%=vet.getNameLast()%> <%
 	break;}}
 %> <%
 	}
 %>
		

	</dhv:evaluate>
	
	
	
	
		<dhv:evaluate if="<%=(visitaPmS != null && visitaPmS.getIdVeterinario1Pm() > 0)%>">
		 <%
				for (String gruppo : listaVeterinari.keySet()) {
			%> <%
 	for (Contact vet : listaVeterinari.get(gruppo)) {
 				if (vet.getUserId() == visitaPmS.getIdVeterinario1Pm()){
 %> <%=vet.getNameLast()%> <%
 	break;}}
 %> <%
 	}
 %>
	</dhv:evaluate>

	<dhv:evaluate if="<%=(visitaPmS != null && visitaPmS.getIdVeterinario2Pm() > 0)%>">
		> <%
				for (String gruppo : listaVeterinari.keySet()) {
			%> <%
 	for (Contact vet : listaVeterinari.get(gruppo)) {
 				if (vet.getUserId() == visitaPmS.getIdVeterinario2Pm()){
 %> <%=vet.getNameLast()%> <%
 	break;}}
 %> <%
 	}
 %>

	</dhv:evaluate>

	<dhv:evaluate if="<%=(visitaPmS != null && visitaPmS.getIdVeterinario3Pm() > 0)%>">
		> <%
				for (String gruppo : listaVeterinari.keySet()) {
			%> <%
 	for (Contact vet : listaVeterinari.get(gruppo)) {
 				if (vet.getUserId() == visitaPmS.getIdVeterinario3Pm()){
 %> <%=vet.getNameLast()%> <%
 	break;}}
 %> <%
 	}
 %>
		

	</dhv:evaluate>



</td>	

<td style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;"><%=(visitaPm != null) ?  (esitiPm.getSelectedValue(visitaPm.getIdEsitoPm())) : (esitiPm.getSelectedValue(visitaPmS.getIdEsitoPm())) %></td>		
<%	
}

%>
</tbody>
</table>


 <div class="fondo">
       <table width="100%">
	   <col width="20%"><col width="50%">
       <tr>
       <td><div align="left"><div class="bollo"><br/>BOLLO</div></div></td>
	   <td align="left">Materiale specifico a rischio distrutto come per legge</td>
       <td><div align="right"><i>IL VETERINARIO UFFICIALE<br/>
		DELL'ASL <%= SiteList.getSelectedValue(macello.getSiteId()) %></i></div></td>
       </tr>       
       </table>
    </div>
    
  <div style="page-break-before:always">&nbsp; </div>  
  
 <div align="left">ALTRE SPECIE *</div>
    <table class="details" cellpadding="5" style="border-collapse: collapse;table-layout:fixed;" width="100%">
<col width="28%"><col width="8%"><col width="10%"><col width="8%"><col width="10%"><col width="6%"><col width="10%"><col width="20%">
<tr>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Auricolare</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Mezzene</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Data di nascita</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Specie</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Categoria</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Sesso</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Rif Mod 4</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Veterinari</th>
<th style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">Esito visita</th>
</tr>
<% for (int s =0; s<6; s++){
	%>
<tr>
<% for (int j = 0; j< 8; j++) { %>
<td style="border:1px solid black;text-overflow: ellipsis; overflow: hidden;">&nbsp;</td>
<% } %></tr>
<% } %>

</table>

</body>
</html>