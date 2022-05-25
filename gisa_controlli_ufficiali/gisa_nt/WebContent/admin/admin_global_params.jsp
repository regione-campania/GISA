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
  - Version: $Id: admin_global_params.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="myCompany"
             class="org.aspcfs.modules.accounts.base.Organization"
             scope="request"/>
<jsp:useBean id="hooks" class="java.lang.String" scope="request"/>
<jsp:useBean id="clients" class="java.lang.String" scope="request"/>
<jsp:useBean id="processes" class="java.lang.String" scope="request"/>
<jsp:useBean id="Timeout" class="java.lang.String" scope="request"/>
<jsp:useBean id="APP_TEXT" class="java.lang.String" scope="application"/>
<jsp:useBean id="merchantPaymentGateway" class="org.aspcfs.modules.admin.base.MerchantPaymentGateway" scope="request"/>
<jsp:useBean id="countrySelect" class="org.aspcfs.utils.web.CountrySelect"
             scope="request"/>
<%@ page import="org.aspcfs.utils.web.HtmlSelectCurrency" %>
<%@ page import="org.aspcfs.utils.web.HtmlSelectLanguage" %>
<%@ page import="org.aspcfs.utils.web.HtmlSelectTimeZone" %>
<%@ page import="org.aspcfs.modules.admin.base.MerchantPaymentGateway" %>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
      <dhv:label name="admin.configureSystem">Configure System</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<% int count = 1; %>
<dhv:permission name="admin-sysconfig-view">
<table cellpadding="4" cellspacing="0" border="0" width="100%"
       class="pagedList">
<tr>
  <dhv:permission name="admin-sysconfig-edit">
    <th width="8">
      &nbsp;
    </th>
  </dhv:permission>
  <th>
    <strong><dhv:label name="admin.parameter">Parameter</dhv:label></strong>
  </th>
  <th>
    <strong><dhv:label name="admin.currentValue">Current
      Value</dhv:label></strong>
  </th>
</tr>

<tr class='row<%= (++count % 2 == 0 ? "1":"2") %>'>
  <dhv:permission name="admin-sysconfig-edit">
    <td align="center">
      <a href="AdminConfig.do?command=Modify&param=MAILSERVER"><dhv:label
          name="accounts.accounts_contacts_oppcomponent_list.Edit">
        Edit</dhv:label></a>
    </td>
  </dhv:permission>
  <td>
    <dhv:label name="admin.emailServer">Email Server</dhv:label>
  </td>
  <td>
    <%= toHtml(getPref(getServletConfig().getServletContext(), "MAILSERVER")) %>
  </td>
</tr>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <dhv:permission name="admin-sysconfig-edit">
    <td align="center">
      <a href="AdminConfig.do?command=Modify&param=EMAILADDRESS"><dhv:label
          name="accounts.accounts_contacts_oppcomponent_list.Edit">
        Edit</dhv:label></a>
    </td>
  </dhv:permission>
  <td>
    <dhv:label name="documents.team.emailAddress">Email Address</dhv:label>
  </td>
  <td>
    <%= toHtml(getPref(getServletConfig().getServletContext(), "EMAILADDRESS")) %>
  </td>
</tr>


<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <td align="center">
    <a href="AdminConfig.do?command=ModifyTimeout&timeout=<%= Timeout %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Edit">Edit</dhv:label></a>
  </td>
  <td>
     <dhv:label name="admin.webSessionTimeout.text">Web Session Timeout (temporary)</dhv:label>
  </td>
  <td>
     <%= Timeout %> <dhv:label name="admin.minutes">minutes</dhv:label>
  </td>
</tr>
</table>
&nbsp;
</dhv:permission>
