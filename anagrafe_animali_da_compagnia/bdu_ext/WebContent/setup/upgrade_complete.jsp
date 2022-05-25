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
  - Version: $Id: upgrade_complete.jsp 24333 2007-12-09 14:51:22Z srinivasar@cybage.com $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="installLog" class="java.util.ArrayList" scope="request" />
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.centricCRMUpgradeComplete">Concourse Suite Community Edition Upgrade Complete!</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.systemUpgraded.text">The system has been upgraded and users can now login. Please follow these steps to make certain the system is usable:</dhv:label>
      <dhv:label name="setup.systemUpgraded.notes"><ul><li>Review the roles, under the Admin module, since the upgrade may have added additional features in which you can now provide access to users by enabling permissions</li><li>Click on various modules and make sure everything appears to work</li></ul></dhv:label>
    </td>
  </tr>
</table>
<br />
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.upgradeLog">Upgrade Log</dhv:label>
    </th>
  </tr>
<dhv:evaluate if="<%= installLog.size() == 0 %>">
  <tr>
    <td>
      <dhv:label name="setup.noScriptsExecuted">No scripts executed</dhv:label>
    </td>
  </tr>
</dhv:evaluate>
<%
Iterator installs = installLog.iterator();
while (installs.hasNext()) {
String step = (String) installs.next();
%>
  <tr>
    <td>
      <%= toHtml(step) %>
    </td>
  </tr>
<%}%>
</table>
<br />
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.nextSteps">Next Steps</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.jspPagesNotYetCompiled.text">Since the JavaServerPages have not yet been compiled, you should choose to precompile the JSPs so that the application works without compile delays. Compiling will occur in the background and you can continue to use Concourse Suite Community Edition.</dhv:label><br />
      <br />
      <input type="button" value="<dhv:label name="button.precompileJsps">Precompile JSPs</dhv:label>" onClick="javascript:popURL('setup/precompile.html','CRM_Compile','500','325','yes','yes')"/><br />
      <br />
      <input type="button" value="<dhv:label name="button.continueToCentricCRMR">Continue to Concourse Suite Community Edition ></dhv:label>" onClick="window.location.href='index.jsp'" />
    </td>
  </tr>
</table>

