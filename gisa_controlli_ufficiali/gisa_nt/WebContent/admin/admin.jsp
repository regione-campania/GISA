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
  - Version: $Id: admin.jsp 24344 2007-12-09 15:18:12Z rambabun@cybage.com $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<dhv:label name="trails.admin">Admin</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="admin.manageApplication.text">Manage this application by reviewing system usage, configuring specific modules, and configuring system parameters.</dhv:label></td>
  </tr>
</table>
<dhv:permission name="admin-users-view,admin-roles-view">
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th>
        <strong><dhv:label name="admin.usersAndRoles" param="amp=&amp;">Users &amp; Roles</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td>
        <ul>
          <dhv:permission name="admin-roles-view"><li><a href="Roles.do?command=ListRoles"><dhv:label name="admin.manageRoles">Manage Roles</dhv:label></a></li></dhv:permission>
        </ul>
        </td>
    </tr>
    
    <tr class="containerBody">
      <td>
        <ul>
          <%if("10.1.15.9".equals(request.getLocalAddr())){ %> 
          <dhv:permission name="admin-users-view"><li><a href="http://151.12.13.135/guc" target="_blank">Gestione Utenti Centralizzata</a></li></dhv:permission>
<% }else
	{%>
             <dhv:permission name="admin-users-view"><li><a href="http://151.12.13.143/guc" target="_blank">Gestione Utenti Centralizzata</a></li></dhv:permission>
         
         <%} %>
        </ul>
        </td>
    </tr>
  </table>
  &nbsp;
</dhv:permission>




<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th>
        <strong><dhv:label name="admin.globalParameters">Global Parameters and Server Configuration</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td>
        <ul>
          <li><a href="AdminConfig.do?command=ListGlobalParams"><dhv:label name="admin.configureSystem">Configure System</dhv:label></a></li>
        </ul>
      </td>
    </tr>
  </table>
  &nbsp;
</dhv:permission>

  

