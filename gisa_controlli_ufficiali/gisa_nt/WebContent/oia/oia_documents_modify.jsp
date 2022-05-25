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
  - Version: $Id: oia_Documenti_modify.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.oia.base.*,com.zeroio.iteam.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.oia.base.Organization" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
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
    if ((form.clientFilename.value) == "") {
      messageText += label("Filename.required", "- Filename is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("Fileinfo.not.submitted", "The file information could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<body onLoad="document.inputForm.subject.focus();">
<form method="post" name="inputForm" action="OiaDocuments.do?command=Update<%= addLinkParams(request, "popup|popupType|actionId") %>" onSubmit="return checkFileForm(this);">
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
<input type="hidden" name="fid" value="<%= FileItem.getId() %>">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Oia.do"><dhv:label name="oia.oia">oia</dhv:label></a> > 
<a href="Oia.do?command=Search"><dhv:label name="oia.SearchResults">Ricerca Dipartimenti/Distretti</dhv:label></a> >
<a href="Oia.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="oia.details">Scheda</dhv:label></a> >
<a href="OiaDocuments.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="oia.oia_Documenti_details.Documenti">Documenti</dhv:label></a> >
<dhv:label name="oia.oia_Documenti_modify.ModifyDocument">Modify Document</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="asl" selected="Documenti" object="OrgDetails" hideContainer='<%= "true".equals(request.getParameter("actionplan")) %>' param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>'>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <% String documentLink = "OiaDocuments.do?command=View&orgId="+OrgDetails.getOrgId(); %>
      <zeroio:folderHierarchy module="oia" link="<%= documentLink %>" />
    </td>
  </tr>
</table>
  <dhv:formMessage />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <img border="0" src="images/file.gif" align="absmiddle"><b><dhv:label name="oia.oia_Documenti_modify.ModifyDocumentInformation">Modify Document Information</dhv:label></b>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="oia.oia_Documenti_modify.SubjectOfFile">Subject of file</dhv:label>
      </td>
      <td>
        <input type="hidden" name="folderId" value="<%= request.getParameter("folderId") %>">
        <input type="text" name="subject" size="59" maxlength="255" value="<%= FileItem.getSubject() %>">
        <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
    
    <%-- 
    <% if (User.getRoleType() == Constants.ROLETYPE_CUSTOMER){ %>
    <input type="hidden" name="allowPortalAccess" value="1"></input>
    <%} else { %>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="oia.oia_document_portal_include.ShareWithPortalUser">Share With Portal User?</dhv:label>
      </td>
      <td>
        <input type="checkbox" name="chk1" value="on" onclick="javascript:setField('allowPortalAccess', document.inputForm.chk1.checked, 'inputForm');" <%= FileItem.getAllowPortalAccess() ? "checked":""%> />
        <input type="hidden" name="allowPortalAccess" value="<%= FileItem.getAllowPortalAccess() ? "1":"0"%>"></input>
      </td>
    </tr>
 		<%}%>
 	--%>
 		
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="oia.oia_Documenti_modify.Filename">Filename</dhv:label>
      </td>
      <td>
        <input type="text" name="clientFilename" size="59" maxlength="255" value="<%= FileItem.getClientFilename() %>">
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="oia.oia_Documenti_details.Version">Version</dhv:label>
      </td>
      <td>
        <%= FileItem.getVersion() %>
      </td>
    </tr>
  </table>
  <br />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="update" />
  <input type="submit" value="Annulla" onClick="javascript:this.form.dosubmit.value='false';this.form.action='OiaDocuments.do?command=View<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>';" />
</dhv:container>
</form>
</body>
