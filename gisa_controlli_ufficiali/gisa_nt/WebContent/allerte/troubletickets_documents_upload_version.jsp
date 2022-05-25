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
  - Version: $Id: troubletickets_documents_upload_version.jsp 15115 2006-05-31 16:47:51Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.allerte.base.*,com.zeroio.iteam.base.*" %>
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.allerte.base.Ticket" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="folderId" class="java.lang.String"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function checkFileForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (form.subject.value == "") {
      messageText += label("Subject.required", "- Subject is required\r\n");
      formTest = false;
    }
    if (form.id<%= TicketDetails.getId() %>.value.length < 5) {
      messageText += label("file.required", "- File is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("File.not.submitted", "The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      if (form.upload.value != label("button.pleasewait","Please Wait...")) {
        form.upload.value=label("button.pleasewait","Please Wait...");
        return true;
      } else {
        return false;
      }
    }
  }
</script>
<body onLoad="document.inputForm.subject.focus();">
<form method="post" name="inputForm" action="TroubleTicketsDocumentsAllerte.do?command=UploadVersion" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="id" value="<%= TicketDetails.getId() %>">
<input type="hidden" name="fid" value="<%= FileItem.getId() %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTicketsAllerte.do"><dhv:label name="sanzionwi">Allerte</dhv:label></a> > 
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTicketsAllerte.do?command=SearchTicketsForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
  <a href="TroubleTicketsAllerte.do?command=SearchTickets"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%}else{%> 
  <a href="TroubleTicketsAllerte.do?command=Home"><dhv:label name="sanzioni.visualizza">Visualizza Allerta</dhv:label></a> >
<%}%>
<a href="TroubleTicketsAllerte.do?command=Details&id=<%= TicketDetails.getId() %>"><dhv:label name="sanzioni.dettagli">Scheda Allerta</dhv:label></a> >
<a href="TroubleTicketsDocumentsAllerte.do?command=View&tId=<%=TicketDetails.getId()%>"><dhv:label name="accounts.accounts_documents_details.Documents">Documents</dhv:label></a> >
<dhv:label name="accounts.accounts_documents_list_menu.AddVersion">Add Version</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="allerte" selected="documents" object="TicketDetails" param="<%= param1 %>">
  <%@ include file="ticket_header_include.jsp" %>
  <table border="0" cellpadding="4" cellspacing="0" width="100%">
    <tr class="subtab">
      <td>
        <% String documentLink = "TroubleTicketsDocumentsAllerte.do?command=View&tId="+TicketDetails.getId(); %>
        <zeroio:folderHierarchy module="TroubleTickets" link="<%= documentLink %>" showLastLink="true"/> >
        <%= FileItem.getSubject() %>
      </td>
    </tr>
  </table>
  <dhv:formMessage showSpace="false"/>
  <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
    <font color="red"><dhv:label name="tickets.alert.closed">This ticket has been closed:</dhv:label>
    <zeroio:tz timestamp="<%= TicketDetails.getClosed() %>" />
    </font><br>
    &nbsp;<br>
  </dhv:evaluate>
  <%-- include add version form --%>
  <%@ include file="documents_addversion_include.jsp" %>
  <p align="center">
    * <dhv:label name="accounts.accounts_documents_upload.LargeFilesUpload">Large files may take a while to upload.</dhv:label><br>
    <dhv:label name="accounts.accounts_documents_upload.WaitForUpload">Wait for file completion message when upload is complete.</dhv:label>
  </p>
  <input type="submit" value=" <dhv:label name="global.button.Upload">Upload</dhv:label> " name="upload">
  <input type="submit" value="Annulla" onClick="javascript:this.form.dosubmit.value='false';this.form.action='TroubleTicketsDocumentsAllerte.do?command=View&tId=<%= TicketDetails.getId() %>&folderId=<%= (String)request.getAttribute("folderId") %>';">
</dhv:container>
</form>
</body>
