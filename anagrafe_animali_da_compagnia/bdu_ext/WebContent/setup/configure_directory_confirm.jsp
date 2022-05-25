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
  - Version: $Id: configure_directory_confirm.jsp 24333 2007-12-09 14:51:22Z srinivasar@cybage.com $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<form name="configure" action="SetupDirectory.do?command=ConfigureDirectoryMake" method="post">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.centricCRM.step1of4.text">Concourse Suite Community Edition Configuration (Step 1 of 4)<br />File Library Settings</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.targetDirectory.note">Note: The specified target directory does not exist. Press continue to create the directory, or cancel to specify a different directory.</dhv:label><br>
      <br>
      <dhv:label name="setup.directoryToCreate.colon">Directory to create:</dhv:label><br>
      <b><%= toHtml(request.getParameter("fileLibrary")) %></b><br>
      <br>
      <input type="hidden" name="fileLibrary" value="<%= toHtmlValue(request.getParameter("fileLibrary")) %>"/>
      <input type="button" value="<dhv:label name="button.cancelL">< Cancel</dhv:label>" onClick="javascript:window.location.href='SetupDirectory.do?command=ConfigureDirectoryCheck&fileLibrary=<%= toJavaScript(request.getParameter("fileLibrary")) %>'"/>
      <input type="submit" value="<dhv:label name="button.continueR">Continue ></dhv:label>"/>
    </td>
  </tr>
</table>
</form>
