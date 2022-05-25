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
  - Version: $Id: troubletickets_documents_list.jsp 15115 2006-05-31 16:47:51Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.allerte_new.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.allerte_new.base.Ticket" scope="request"/>
<jsp:useBean id="TicketDocumentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="troubletickets_documents_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/confirmDelete.js"></script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTicketsAllerteNew.do"><dhv:label name="sanzioniaa">Allerte</dhv:label></a> > 
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTicketsAllerteNew.do?command=SearchTicketsForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
  <a href="TroubleTicketsAllerteNew.do?command=SearchTickets"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%}else{%> 
  <a href="TroubleTicketsAllerteNew.do?command=Home"><dhv:label name="sanzioni.visualizzad">Visualizza Allerta</dhv:label></a> >
<%}%>
<a href="TroubleTicketsAllerteNew.do?command=Details&id=<%= TicketDetails.getId() %>"><dhv:label name="sanzioni.dettegli">Scheda Allerta</dhv:label></a> >
<dhv:label name="accounts.accounts_documents_details.Documents">Documents</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="allerte" selected="documents" object="TicketDetails" param="<%= param1 %>">
  <%@ include file="ticket_header_include.jsp" %>
  <%
    String permission_doc_folders_add = "allerte-allerte-documents-edit";
    String permission_doc_files_upload = "allerte-allerte-documents-edit";
    String permission_doc_folders_edit = "allerte-allerte-documents-edit";
    String documentFolderAdd ="TroubleTicketsDocumentsFoldersAllerte.do?command=Add&tId="+TicketDetails.getId()+"&column=subject";
    String documentFileAdd = "TroubleTicketsDocumentsAllerte.do?command=Add&tId="+TicketDetails.getId();
    String documentFolderModify = "TroubleTicketsDocumentsFoldersAllerte.do?command=Modify&tId="+TicketDetails.getId();
    String documentFolderList = "TroubleTicketsDocumentsAllerte.do?command=View&tId="+TicketDetails.getId()+"&column=subject";
    String documentFileDetails = "TroubleTicketsDocumentsAllerte.do?command=Details&tId="+TicketDetails.getId();
    String documentModule = "HelpDesk";
    String specialID = ""+TicketDetails.getId();
    boolean hasPermission = true;
  %>
  <%@ include file="../accounts/documents_list_include.jsp" %>
</dhv:container>