<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004-2006 Concursive Corporation (http://www.concursive.com/) All
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
  - Version: $Id: language.jsp 24333 2007-12-09 14:51:22Z srinivasar@cybage.com $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.utils.web.HtmlSelectLanguage" %>
<%@ page import="org.aspcfs.utils.web.HtmlSelect" %>
<script language="JavaScript">
  function checkForm(form) {
    if (form.language.options.selectedIndex == -1) {
      alert(label("select.language","Please select a language to continue"));
      return false;
    }
    return true;
  }
</script>
<form name="language" action="Setup.do?command=Welcome" method="post" onsubmit="return checkForm(this);">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th colspan="2"><dhv:label name="setup.centricCRMInstallation">Concourse Suite Community Edition Installation</dhv:label></th>
  </tr>
  <tr>
    <td colspan="2">
      <dhv:label name="setup.chooseLanguageToContinue.colon">Choose a language to continue:</dhv:label><br />
    </td>
  </tr>
  <tr>
    <td nowrap>
      <% HtmlSelect selectLanguage = HtmlSelectLanguage.getSelect("language", "None Selected");
         selectLanguage.setSelectSize(selectLanguage.size());
         %><%= selectLanguage.getHtml("language", " ") %>
    </td>
    <td valign="top" width="100%">
      <input type="submit" value=">"/><br />
    </td>
  </tr>
</table>
</form>
