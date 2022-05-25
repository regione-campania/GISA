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
  - Author(s): 
  - Version: $Id: document_store_center_file_modify.jsp 11310 2005-04-13 20:05:00Z mrajkowski $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*, org.aspcfs.modules.documents.base.*" %>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.subject.focus();">
<script language="JavaScript">
  function checkFileForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (form.subject.value == "") {
      messageText += label("Subject.required","- Subject is required\r\n");
      formTest = false;
    }
    if ((form.clientFilename.value) == "") {
      messageText += label("Filename.required","- Filename is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      form.dosubmit.value = "true";
      messageText = label("Fileinfo.not.submitted","The file information could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="POST" name="inputForm" action="DocumentStoreManagementFiles.do?command=Update" onSubmit="return checkFileForm(this);">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <% String documentLink = "DocumentManagement.do?command=DocumentStoreCenter&section=File_Library&documentStoreId="+documentStore.getId(); %>
      <zeroio:folderHierarchy module="Documents" link="<%= documentLink %>" showLastLink="true"/> >
      <%= FileItem.getSubject() %>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="<dhv:label name="documents.documents.updateFile">Update</dhv:label>" name="update">
<input type="submit" value="<dhv:label name="documents.documents.cancelUpdate">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='DocumentManagement.do?command=DocumentStoreCenter&section=File_Library&documentStoreId=<%= documentStore.getId() %>';"><br>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="7">
      <strong><dhv:label name="documents.documents.modifyFileInformation">Modify File Information</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="documents.documents.subject">Subject</dhv:label></td>
    <td>
      <input type="text" name="subject" size="59" maxlength="255" value="<%= FileItem.getSubject() %>">
      <%= showAttribute(request, "subjectError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="documents.documents.fileName">File Name</dhv:label></td>
    <td>
      <input type="text" name="clientFilename" size="59" maxlength="255" value="<%= FileItem.getClientFilename() %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="documents.documents.currentVersion" param="break=<br />">Current<br />Version</dhv:label></td>
    <td valign="top">
      <%= FileItem.getVersion() %>
    </td>
  </tr>
</table>
<br>
<input type="hidden" name="folderId" value="<%= FileItem.getFolderId() %>">
<input type="submit" value="<dhv:label name="documents.documents.updateFile">Update</dhv:label>" name="update">
<input type="submit" value="<dhv:label name="documents.documents.cancelUpdate">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='DocumentManagement.do?command=DocumentStoreCenter&section=File_Library&documentStoreId=<%= documentStore.getId() %>';"><br>
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="documentStoreId" value="<%= documentStore.getId() %>">
<input type="hidden" name="fid" value="<%= FileItem.getId() %>">
</form>
</body>
