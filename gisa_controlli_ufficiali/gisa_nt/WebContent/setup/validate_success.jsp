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
  - Version: $Id: validate_success.jsp 24333 2007-12-09 14:51:22Z srinivasar@cybage.com $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<form name="configure" action="SetupDirectory.do?command=ConfigureDirectoryCheck" method="post">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.licenseAccepted">License accepted!</dhv:label><br>
      <br>
      <dhv:label name="setup.timeToConfigureSystemSettings.text">Now it's time to configure some of the system settings before you can begin using Concourse Suite Community Edition.</dhv:label><br>
      <br>
      <dhv:label name="setup.configurationIncludes.colon">Configuration includes the following steps:</dhv:label><br>
      <br>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.stepOne.colon">Step 1:</dhv:label>
          </td>
          <td>
            <dhv:label name="setup.setupFileLibrary">Setup the file library</dhv:label>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.step2.colon">Step 2:</dhv:label>
          </td>
          <td>
            <dhv:label name="setup.setupExternalServers">Setup external servers (mail, fax)</dhv:label>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.step3.colon">Step 3:</dhv:label>
          </td>
          <td>
            <dhv:label name="setup.setupDatabase">Setup the database</dhv:label>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.step4.colon">Step 4:</dhv:label>
          </td>
          <td>
            <dhv:label name="setup.setupCentricCRMAdminUserAccount">Setup the Concourse Suite Community Edition administrative user account</dhv:label>
          </td>
        </tr>
      </table>
      <br>
      <input type="submit" value="<dhv:label name="button.continueR">Continue ></dhv:label>"/>
    </td>
  </tr>
</table>
</form>
