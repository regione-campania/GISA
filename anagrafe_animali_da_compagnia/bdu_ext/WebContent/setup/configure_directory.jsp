<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: configure_directory.jsp 24333 2007-12-09 14:51:22Z srinivasar@cybage.com $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="fileLibrary" class="java.lang.String" scope="request"/>
<body onLoad="javascript:document.configure.fileLibrary.focus();">
<script language="JavaScript">
  function checkForm(form) {
    valid = true;
    message = "";
    if (form.fileLibrary.value.length == 0) {
      message += label("check.targetdirectory","- Target directory is a required field\r\n");
      valid = false;
    }
    if (valid == false) {
      alert(label("check.form","Form could not be submitted, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
</script>
<dhv:formMessage showSpace="false" />
<form name="configure" action="SetupDirectory.do?command=ConfigureDirectory" method="post" onSubmit="return checkForm(this)">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.centricCRM.step1of4.text">Concourse Suite Community Edition Configuration (Step 1 of 4)<br />File Library Settings</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.usersFileStore.question">Users will have the capability to upload and create files on the server.<br />Where should Concourse Suite Community Edition store files that get created?</dhv:label><br>
      <dhv:label name="setup.targetDirectoryFileStore.text"><ul><li>If the target directory does not exist it will be created</li><li>The target directory should have plenty of free storage space for uploads</li><li>The target directory must have write permissions</li><li>The target directory should be backed up often to prevent data loss</li><li>The target directory should not be located in the servlet path to make upgrades easier</li></ul></dhv:label>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.targetDirectory.colon">Target Directory:</dhv:label>
          </td>
          <td nowrap>
            <input type="text" name="fileLibrary" value="<%= toHtmlValue(fileLibrary) %>" size="50"/>
          </td>
        </tr>
      </table>
      <br>
      <input type="submit" value="<dhv:label name="button.continueR">Continue ></dhv:label>"/>
    </td>
  </tr>
</table>
</form>
</body>
