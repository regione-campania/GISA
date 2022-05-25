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
  - Version: $Id: configure_database_success.jsp 24333 2007-12-09 14:51:22Z srinivasar@cybage.com $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="APP_VERSION" class="java.lang.String" scope="application"/>
<jsp:useBean id="database" class="org.aspcfs.modules.setup.beans.DatabaseBean" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript">
  function showProgress() {
    hideSpan("buttons");
    showSpan("progress");
    return true;
  }
</script>
<form name="configure" action="SetupDatabase.do?command=ConfigureDatabaseData" method="post" onSubmit="return showProgress()">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.configuration.step3to4">Concourse Suite Community Edition Configuration (Step 3 of 4)<br />Database Settings &amp; Installation</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.databaseConnectionSuccessful">Database Connection Successful!</dhv:label>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
<dhv:evaluate if="<%= hasText(APP_VERSION) %>">
  <tr>
    <td>
      <dhv:label name="setup.createDatabaseTables.text">Concourse Suite Community Edition will now create all of the necessary database tables and initial data in the following database:</dhv:label><br>
      <br>
      <b><%= toHtmlValue(database.getUrl()) %></b><br>
      <br>
      If restoring from a Concourse Suite Community Edition XML backup file, specify the file on the local server to restore from:<br/>
      <input type="text" name="backupFile" value="" size="40" /><br />
      <br />
      <dhv:label name="setup.takesFewMinutes.text">This can take a few minutes depending on connectivity to the database, select Continue to begin.</dhv:label><br>
      <br>
      <span id="buttons" name="buttons">
        <input type="submit" value="<dhv:label name="button.continueR">Continue ></dhv:label>"/>
      </span>
      <span id="progress" name="progress" style="display:none">
        <font color="blue"><b><dhv:label name="setup.pleaseWait.creatingDatabase.text">Please Wait... creating the database!</b> This could take up to 5 minutes...</dhv:label></font>
      </span>
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= !hasText(APP_VERSION) %>">
  <tr>
    <td>
      The Concourse Suite Community Edition Community Edition requires that the necessary database tables and initial data have been installed using &quot;ant installdb&quot; in the following database:<br />
      <br>
      <b><%= toHtmlValue(database.getUrl()) %></b><br>
      <br>
      The database schema will now be verified.<br />
      <br>
      <span id="buttons" name="buttons">
        <input type="submit" value="<dhv:label name="button.continueR">Continue ></dhv:label>"/>
      </span>
      <span id="progress" name="progress" style="display:none">
        <font color="blue"><b>Please Wait... testing the database!</b></font>
      </span>
    </td>
  </tr>
</dhv:evaluate>
</table>
</form>
