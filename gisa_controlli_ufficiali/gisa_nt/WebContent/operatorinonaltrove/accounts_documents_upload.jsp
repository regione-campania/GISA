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
  - Version: $Id: accounts_documents_upload.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.operatorinonaltrove.base.*,com.zeroio.iteam.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.operatorinonaltrove.base.Organization" scope="request"/>
<jsp:useBean id="folderId" class="java.lang.String"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/tasks.js"></script>
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
    if (form.id<%= OrgDetails.getOrgId() %>.value.length < 5) {
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
<form method="post" name="inputForm" action="OpnonAltroveDocuments.do?command=Upload<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
<input type="hidden" name="dosubmit" value="true" />
<input type="hidden" name="id" value="<%= OrgDetails.getOrgId() %>" />
<input type="hidden" name="folderId" value="<%= (String)request.getAttribute("folderId") %>" />
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="OpnonAltrove.do">Operatori non presenti altrove</a> > 
<a href="OpnonAltrove.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="OpnonAltrove.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Scheda</a> >
<a href="OpnonAltroveDocuments.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.accounts_documents_details.Documents">Documents</dhv:label></a> >
<dhv:label name="accounts.accounts_documents_upload.UploadDocument">Upload Document</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="operatorinonaltrove" selected="documents" object="OrgDetails" hideContainer='<%= "true".equals(request.getParameter("actionplan")) %>' param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>'>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <% String documentLink = "OpnonAltroveDocuments.do?command=View&orgId="+OrgDetails.getOrgId()+ addLinkParams(request, "popup|popupType|actionId|actionplan"); %>
      <zeroio:folderHierarchy module="Accounts" link="<%= documentLink %>" />
    </td>
  </tr>
</table>
  <dhv:formMessage />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <img border="0" src="images/file.gif" align="absmiddle"><b><dhv:label name="accounts.accounts_documents_upload.UploadNewDocument">Upload a New Document</dhv:label></b>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
      </td>
      <td>
        <input type="text" name="subject" size="59" maxlength="255" value="<%= toHtmlValue((String)request.getAttribute("subject")) %>"><font color="red">*</font>
        <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
    
   
 		
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
      </td>
      <td>
        <input type="file" name="id<%= OrgDetails.getOrgId() %>" size="45">
      </td>
    </tr>
  </table>
  <p align="center">
    * <dhv:label name="accounts.accounts_documents_upload.LargeFilesUpload">Large files may take a while to upload.</dhv:label><br>
    <dhv:label name="accounts.accounts_documents_upload.WaitForUpload">Wait for file completion message when upload is complete.</dhv:label>
  </p>
  <input type="submit" value="<dhv:label name="global.button.Upload">Upload</dhv:label> " name="upload" />
  <input type="submit" value="Annulla" onClick="javascript:this.form.dosubmit.value='false';this.form.action='OpnonAltroveDocuments.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&folderId=<%= (String)request.getAttribute("folderId") %><%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>';" />
</dhv:container>
</form>
</body>
