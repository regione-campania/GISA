<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
 

<%@page import="java.net.InetAddress"%>
<%@page import="org.aspcfs.modules.lineeattivita.base.*"%>
<%@page import="java.util.ArrayList" %>
<%@page import="org.aspcfs.modules.allevamenti.base.OrganizationAddress" %>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.allevamenti.base.Organization" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheda</title>
</head>
<link rel="stylesheet" type="text/css" media="all" documentale_url="" href="gestione_documenti/schede/schede_layout.css" />

<body>

<table width="100%">
<col width="33%"><col width="33%"><col width="33%">
<tr>
<td><div align="left"><img style="text-decoration: none;" width="80" height="80" documentale_url="" src="gestione_documenti/schede/images/regionecampania.jpg" /></div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

</td>
<td><center><b><label class="titolo">Allevamenti</label></b></center>
</td>
<td><div align="right"><img style="text-decoration: none;" height="80" documentale_url="" src="gestione_documenti/schede/images/<%=SiteList.getSelectedValue(orgDetails.getSiteId()).toLowerCase() %>.jpg" /></div>
</td>
</tr>
</table>

<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="20%">
<tr><td colspan="4" class="grey"><b>Informazione primaria</b></td></tr>
<tr><td class="blue" >Impresa</td>
<td class="layout"><%=orgDetails.getBanca() %></td></tr>
<tr><td class="blue">Specie allevata</td>
<td class="layout"> <%=orgDetails.getSpecieAllev() %></td></tr>
<tr><td class="blue">Codice azienda</td>
<td class="layout"> <%=orgDetails.getAccountNumber() %></td></tr>
<tr><td class="blue">Categoria di rischio</td>
<td class="layout"> <%= (((orgDetails.getCategoriaRischio()>0))?(orgDetails.getCategoriaRischio()):("3"))%></td></tr>
<tr><td class="blue">Prossimo Controllo con la tecnica della Sorveglianza</td>
<td class="layout">    <% java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
		Timestamp d = new Timestamp (datamio.getTime()); %>
      <% SimpleDateFormat dataPC = new SimpleDateFormat("dd/MM/yyyy");
       %>
         <%= (((orgDetails.getData_prossimo_controllo()!=null))?(dataPC.format(orgDetails.getData_prossimo_controllo())):(dataPC.format(d)))%></td></tr>
<tr><td class="blue">Codice Fiscale Proprietario</td>
<td class="layout"> <%= toHtml(orgDetails.getCodiceFiscaleRappresentante()) %></td></tr>
<tr><td class="blue">Codice Fiscale Detentore</td>
<td class="layout"> <%= toHtml(orgDetails.getCodiceFiscaleCorrentista()) %></td></tr>
<tr><td class="blue">Orientamento</td>
<td class="layout"> <%=orgDetails.getOrientamentoProd() %> </td></tr>
<tr><td class="blue">Struttura</td>
<td class="layout"><%=orgDetails.getTipologiaStrutt() %> </td></tr>
<tr><td class="blue">Data inizio attività</td>
<td class="layout"><%= toDateasString(orgDetails.getDate1()) %></td></tr>
<tr><td class="blue">Stato allevamento</td>
<td class="layout"> <%= toDateasString(orgDetails.getDate2()) %></td></tr>

<%  
  Iterator iaddress = orgDetails.getAddressList().iterator();
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      OrganizationAddress thisAddress = (OrganizationAddress)iaddress.next();
	if(thisAddress.getType()==1)
	{%>
	<tr><td class="blue">Sede legale</td>
<td class="layout"> <%=(thisAddress.getStreetAddressLine1()!=null) ? thisAddress.getStreetAddressLine1() : ""%>&nbsp;</td></tr>
<tr><td class="blue"></td>
<td class="layout"> <%=(thisAddress.getCity()!=null) ? thisAddress.getCity() : "" %>&nbsp;</td></tr>
<tr><td class="blue"></td>
<td class="layout"> <%=(thisAddress.getZip()!=null) ? thisAddress.getZip() : "" %>&nbsp; <%=(thisAddress.getState()!=null) ? thisAddress.getState() : "" %>&nbsp;</td></tr>
<tr><td class="blue"></td>
<td class="layout"><%=thisAddress.getLatitude() %> <%=thisAddress.getLongitude()%></td></tr>
<% } 
	else if(thisAddress.getType()==5)
	{%>
	<tr><td class="blue">Sede operativa</td>
<td class="layout"> <%=thisAddress.getStreetAddressLine1()%></td></tr>
<tr><td class="blue"></td>
<td class="layout"> <%=thisAddress.getCity() %></td></tr>
<tr><td class="blue"></td>
<td class="layout"> <%=thisAddress.getZip() %> <%=thisAddress.getState() %></td></tr>
<tr><td class="blue"></td>
<td class="layout"><%=thisAddress.getLatitude() %> <%=thisAddress.getLongitude()%></td></tr>
<% }   } }%>
<tr><td class="blue">Email</td>
<td class="layout"><%=(orgDetails.getEmailRappresentante() != null) ? orgDetails.getEmailRappresentante() : "" %></td></tr>
<tr><td class="blue">Telefono</td>
<td class="layout"> <%=(orgDetails.getTelefonoRappresentante()!=null) ? orgDetails.getTelefonoRappresentante() : "" %></td></tr>
</table>

<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="20%">
<tr><td colspan="4" class="grey"><b>Proprietario</b></td></tr>
  
 <%if (orgDetails.getNominativoProp()!=null && !orgDetails.getNominativoProp().equals("")){ %>
   <tr>
      <td class="blue">Nominativo</td>
      <td class="layout">
        <%= toHtml(orgDetails.getNominativoProp()) %>        
      </td>
    </tr>
    <%} %>
    
     <tr>
      <td class="blue">
       Codice Fiscale
      </td>
      <td class="layout">
        <%= toHtml(orgDetails.getCodiceFiscaleRappresentante()) %>        
      </td>
    </tr>
   
   <%if ((orgDetails.getIndirizzoProp()!= null && !orgDetails.getIndirizzoProp().trim().equals("")) || (orgDetails.getComuneProp()!= null && !orgDetails.getComuneProp().trim().equals(""))){ %>
    <tr>
      <td class="blue">
       Indirizzo
      </td>
      <td class="layout">
        <%= toHtml(orgDetails.getIndirizzoProp()) + ","+ toHtml(orgDetails.getProvProp())+ "<br>" + toHtml(orgDetails.getComuneProp()) + ","+ toHtml(orgDetails.getCapProp()) %>        
      </td>
    </tr>
    <%} %>
  
  </table>
<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="20%">
 <tr><td colspan="4" class="grey"><b>Detentore</b></td></tr>  
 <%if (orgDetails.getNominativoDetentore()!=null && !orgDetails.getNominativoDetentore().equals("")){ %>
   <tr>
      <td class="blue">
       Nominativo
      </td>
      <td class="layout">
        <%= toHtml(orgDetails.getNominativoDetentore()) %>        
      </td>
    </tr>
    <%} %>
    
     <tr>
      <td class="blue">
       Codice Fiscale
      </td>
      <td class="layout">
        <%= toHtml(orgDetails.getCodiceFiscaleCorrentista()) %>        
      </td>
    </tr>
   
   <%if ((orgDetails.getIndirizzoDetentore()!= null && !orgDetails.getIndirizzoDetentore().trim().equals("")) || (orgDetails.getComuneDetentore()!= null && !orgDetails.getComuneDetentore().trim().equals(""))){ %>
    <tr>
      <td class="blue">
       Indirizzo
      </td>
      <td class="layout">
        <%= toHtml(orgDetails.getIndirizzoDetentore()) + ","+ toHtml(orgDetails.getProvDetentore())+ "<br>" + toHtml(orgDetails.getComuneDetentore()) + ","+ toHtml(orgDetails.getCapDetentore()) %>        
      </td>
    </tr>
    <%} %>
  
  </table>

<br/>

<table cellpadding="5" style="border-collapse: collapse; page-break-inside:avoid" width="100%">
<col width="20%">
<tr><td colspan="4" class="grey"><b>Dettagli addizionali</b></td></tr>
<tr><td class="blue" ><br/><br/><br/></td>
<td class="layout"><%=(orgDetails.getNotes()!=null) ? orgDetails.getNotes() : "" %></td></tr>
</table>
</body>
</html>