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
  - Version: $Id: upgrade_error.jsp 24333 2007-12-09 14:51:22Z srinivasar@cybage.com $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="errorMessage" class="java.lang.String" scope="request" />
<jsp:useBean id="installLog" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="installedVersion" class="java.lang.String" scope="request"/>
<jsp:useBean id="newVersion" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table border="0" width="100%">
  <tr>
    <td>
      <img src="images/error.gif" border="0" align="absmiddle"/>
      <dhv:label name="setup.errorOccuredDuringUpgrade">An error occurred during the upgrade!</dhv:label>
    </td>
  </tr>
</table>
<br />
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.upgradeFailed">Concourse Suite Community Edition Upgrade Failed!</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.databaseInconsistentState.text">Your database could be in an inconsistent state. You might want to seek assistance with what could have gone wrong. This page might contain some useful information during that analysis.</dhv:label>
    </td>
  </tr>
</table>
<br />
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.suggestions">Suggestions</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.restoreDatabaseToBackup.text">If you are in a hurry to get the system working, you could restore the database to your last backup and then put the previous version of Concourse Suite Community Edition back online.</dhv:label>
    </td>
  </tr>
</table>
<br />
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.errorLog">Error log</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
    <% String temp = "installedVersion="+installedVersion+"|newVersion="+newVersion; %>
      <dhv:label name="setup.errorDetails.fromVersionToVersion.text" param="<%= temp %>">While trying to upgrade from '<%= installedVersion %>' to '<%= newVersion %>' an error occurred.<br />The following error message was provided:</dhv:label><br />
      &nbsp;
    </td>
  </tr>
  <tr>
    <td>
      <%= toHtml(errorMessage) %>
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
      <dhv:label name="setup.noScriptsCompletedSuccessfully">No scripts completed successfully</dhv:label>
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

