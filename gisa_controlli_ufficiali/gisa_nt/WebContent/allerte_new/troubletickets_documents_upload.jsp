<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: troubletickets_documents_upload.jsp 15115 2006-05-31 16:47:51Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*" %>
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="folderId" class="java.lang.String"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<script>
  function allegaEInserisci()
  {
	  document.inputForm.action = 'TroubleTicketsDocumentsAllerte.do?command=AllegaEInserisci';

	if (document.inputForm.subject.value == "")
	{
		alert("- Inserire una descrizione per il campo oggetto");
	}
	else
		 document.inputForm.submit();
  }
  </script>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.subject.focus();">
 <%
  String header = null;
  if(request.getAttribute("header")!=null)
  {
	  header = (String) request.getAttribute("header");
	  %>
	  
	  <% 
  }
  String chiusura = null;
  if(request.getAttribute("chiusuraUfficio")!=null)
  {
	  chiusura = (String) request.getAttribute("chiusuraUfficio");
  }
  %>

<%if(header != null){
	
	if (chiusura != null)
	{
		%>
		<form method="post" name="inputForm" action="TroubleTicketsAllerteNew.do?command=UploadEClose&chiusuraUfficio=1&id=<%=TicketDetails.getId() %>" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
		
	<%	
	}
	else{
		%>
		<form method="post" name="inputForm" action="TroubleTicketsAllerteNew.do?command=UploadEClose&id=<%=TicketDetails.getId() %>" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
		
		
		<%
	}
	
}else{
	%>

<form method="post" name="inputForm" action="TroubleTicketsDocumentsAllerte.do?command=Upload" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">

<%} %>
<%if (header != null)
	{
	%>
	<input type = "hidden" name = "header" value="<%=header %>" >
	
	<%
	
	}
	%>
	
	<%if (chiusura != null)
	{
	%>
	<input type = "hidden" name = "chiusura" value="<%=chiusura %>" >
	
	<%
	
	}
	%>

<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="id" value="<%= TicketDetails.getId() %>">
<input type="hidden" name="folderId" value="<%= (String)request.getAttribute("folderId") %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTicketsAllerteNew.do"><dhv:label name="sanzioniss">Allerte</dhv:label></a> > 
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTicketsAllerteNew.do?command=SearchTicketsForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
  <a href="TroubleTicketsAllerteNew.do?command=SearchTickets"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%}else{%> 
  <a href="TroubleTicketsAllerteNew.do?command=Home"><dhv:label name="sanzioni.visualizzas">Visualizza allerte</dhv:label></a> >
<%}%>
<a href="TroubleTicketsAllerteNew.do?command=Details&id=<%= TicketDetails.getId() %>"><dhv:label name="sanzioni.dettagliss">Scheda Allerta</dhv:label></a> >
<a href="TroubleTicketsDocumentsAllerte.do?command=View&tId=<%=TicketDetails.getId()%>"><dhv:label name="sanzioni.documenti">Documenti</dhv:label></a> >
<dhv:label name="aggiungi_documento">Aggiungi Documento</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="allerte" selected="documents" object="TicketDetails" param="<%= param1 %>">
  <%@ include file="ticket_header_include.jsp" %>
  
 
  
  <br/>
  

  <table border="0" cellpadding="4" cellspacing="0" width="100%">
    <tr class="subtab">
      <td>
        <% String documentLink = "TroubleTicketsDocumentsAllerte.do?command=View&tId="+TicketDetails.getId(); %>
        <zeroio:folderHierarchy module="TroubleTickets" link="<%= documentLink %>" showLastLink="false"/>
      </td>
    </tr>
  </table>
  <%@ include file="documents_add_include.jsp" %>
  <br><br>
  <input type = "button" value = "Invia e Allega Altro File" onclick="allegaEInserisci();">
  
  
  <p align="center">
    * <dhv:label name="accounts.accounts_documents_upload.LargeFilesUpload">Large files may take a while to upload.</dhv:label><br>
    <dhv:label name="accounts.accounts_documents_upload.WaitForUpload">Wait for file completion message when upload is complete.</dhv:label>
  </p>
  <input type="submit" <%if(request.getAttribute("header")!=null)  { %> value = "Carica File e chiudi Allerta" <%}else { %>value=" <dhv:label name="global.button.Upload">Upload</dhv:label> " <%} %>  name="upload">
  <input type="submit" value="Annulla" onClick="javascript:this.form.dosubmit.value='false';this.form.action='TroubleTicketsDocumentsAllerte.do?command=View&tId=<%= TicketDetails.getId() %>&folderId=<%= (String)request.getAttribute("folderId") %>';">
</dhv:container>
<%
if (request.getAttribute("tipoAlimenti")!=null)
{
	%>
	<input type = "hidden" name = "tipoAlimenti" value = "<%=request.getAttribute("tipoAlimenti") %>">
	<input type = "hidden" name = "specie_alimenti" value = "<%=request.getAttribute("specie_alimenti") %>">
	
	<%
}
%>

</form>

