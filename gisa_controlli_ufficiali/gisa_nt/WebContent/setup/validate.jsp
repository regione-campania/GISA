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
  - Version: $Id: validate.jsp 24333 2007-12-09 14:51:22Z srinivasar@cybage.com $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function checkForm(form) {
    if (form.license.value.length == 0) {
      alert(label("check.licensekey","Enter the license key in the field to continue"));
      return false;
    }
    return true;
  }
</script>
<body onLoad="javascript:document.register.license.focus();">
<dhv:formMessage showSpace="false" />
<form name="register" action="Setup.do?command=Validate" method="post" onSubmit="return checkForm(this)">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th><dhv:label name="setup.information">Information</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.ifRegisteredLicenceByEmail.text">If you have already registered for Concourse Suite Community Edition, you should have received a license file by email.</dhv:label><br>
      <ul>
      <li><dhv:label name="setup.licenseOnlyUsedOnSystem.text">The license can only be used on the system that requested it</dhv:label></li>
      <li><dhv:label name="setup.installationIssues.license.text" param="license=<a href=\"Setup.do?command=Register\">|endLicense=</a>">If you have misplaced your license or you are installing Concourse Suite Community Edition on a different server, then you can <a href="Setup.do?command=Register">request a new license</a> to be sent by email</dhv:label></li>
      </ul>
    </td>
  </tr>
  <tr class="sectionTitle">
    <th><dhv:label name="setup.validation">Validation</dhv:label></th>
  </tr>
  <tr>
    <td nowrap>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.licenseKey">License Key</dhv:label>
          </td>
          <td nowrap>
            <dhv:label name="setup.pasteYourLicenseToContinue.text">Paste your license into the text field to continue:</dhv:label><br>
            <textarea cols="60" rows="5" name="license"></textarea>
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
