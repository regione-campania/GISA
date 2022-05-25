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
  - Version: $Id: configure_global_items.jsp 24333 2007-12-09 14:51:22Z srinivasar@cybage.com $
  - Description:
  --%>
<%-- Displays any global items for this setup page --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request"/>
<%
  String image = "<img alt=\"Current Step\" src=\"images/bullet.gif\" align=\"absmiddle\"/>&nbsp;";
%>
<table width="100%" border="0">
  <tr>
    <td><b><dhv:label name="setup.centricCRMInstallation">Concourse Suite Community Edition Installation</dhv:label></b></td>
  </tr>
</table>
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th colspan="2">
      <dhv:label name="setup.youWereHere.label">You are here...</dhv:label>
    </th>
  </tr>
  <tr>
    <td nowrap valign="top"><dhv:evaluate if='<%= "Welcome".equals(ModuleBean.getCurrentAction()) %>'><%= image %></dhv:evaluate></td>
    <td valign="top" width="100%"><dhv:label name="Welcome">Welcome</dhv:label></td>
  </tr>
  <tr>
    <td nowrap valign="top"><dhv:evaluate if='<%= "Register".equals(ModuleBean.getCurrentAction()) %>'><%= image %></dhv:evaluate></td>
    <td valign="top">Registration</td>
  </tr>
  <tr>
    <td nowrap valign="top"><dhv:evaluate if='<%= "Storage".equals(ModuleBean.getCurrentAction()) %>'><%= image %></dhv:evaluate></td>
    <td valign="top">Configure Storage</td>
  </tr>
  <tr>
    <td nowrap valign="top"><dhv:evaluate if='<%= "Servers".equals(ModuleBean.getCurrentAction()) %>'><%= image %></dhv:evaluate></td>
    <td valign="top">Configure Servers</td>
  </tr>
  <tr>
    <td nowrap valign="top"><dhv:evaluate if='<%= "Database".equals(ModuleBean.getCurrentAction()) %>'><%= image %></dhv:evaluate></td>
    <td valign="top">Configure Database</td>
  </tr>
  <tr>
    <td nowrap valign="top"><dhv:evaluate if='<%= "CRMSetup".equals(ModuleBean.getCurrentAction()) %>'><%= image %></dhv:evaluate></td>
    <td valign="top">Configure CRM</td>
  </tr>
</table>
<br>
<%-- info tab --%>
<dhv:evaluate if='<%= "Welcome".equals(ModuleBean.getCurrentAction()) %>'>
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      <dhv:label name="Welcome">Welcome</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.acquireRetainAndServiceCustomers.text">Acquire, retain, and service your customers with Concourse Suite Community Edition.</dhv:label><br>
      <br>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="http://www.concursive.com" target="_blank"><dhv:label name="setup.learnMore.text">Learn more about our products</dhv:label></a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- info tab --%>
<dhv:evaluate if='<%= "Register".equals(ModuleBean.getCurrentAction()) %>'>
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      <dhv:label name="setup.registration">Registration</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.privacyStatement.text">Privacy is extremely important to us.<br /><br />Your information will not be provided to others.</dhv:label><br>
      <br>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="javascript:popURL('privacy_statement.html','CRM_Privacy','400','400','yes','yes');"><dhv:label name="setup.reviewPrivacyStatement">Review Privacy Statement</dhv:label></a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- info tab --%>
<dhv:evaluate if='<%= "Storage".equals(ModuleBean.getCurrentAction()) %>'>
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      <dhv:label name="setup.storageResources">Storage Resources</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.backupReminder.text">Remember to make backups of your data.</dhv:label><br>
      <br>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="http://www.concursive.com" target="_blank"><dhv:label name="setup.fileBackupHelp">File Backup Help</dhv:label></a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- info tab --%>
<dhv:evaluate if='<%= "Servers".equals(ModuleBean.getCurrentAction()) %>'>
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      <dhv:label name="setup.serverResources">Server Resources</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="http://www.concursive.com" target="_blank"><dhv:label name="setup.sendmailHelp">Sendmail Help</dhv:label></a><br>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="http://www.concursive.com" target="_blank"><dhv:label name="setup.hylafaxHelp">HylaFax Help</dhv:label></a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- info tab --%>
<dhv:evaluate if='<%= "Database".equals(ModuleBean.getCurrentAction()) %>'>
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      <dhv:label name="setup.databaseResources">Database Resources</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="http://www.concursive.com" target="_blank"><dhv:label name="setup.postgreSQLHelp">PostgreSQL Help</dhv:label></a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- info tab --%>
<dhv:evaluate if='<%= "CRMSetup".equals(ModuleBean.getCurrentAction()) %>'>
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      <dhv:label name="setup.centricCRMResources">Concourse Suite Community Edition Resources</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="http://www.concursive.com" target="_blank"><dhv:label name="setup.centricCRMHelp">Concourse Suite Community Edition Help</dhv:label></a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
