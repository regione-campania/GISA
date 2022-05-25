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
  - Version: $Id: admin_roleinsertform.jsp 24344 2007-12-09 15:18:12Z rambabun@cybage.com $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.Permission, java.util.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="Role" class="org.aspcfs.modules.admin.base.Role" scope="request"/>
<jsp:useBean id="PermissionList" class="org.aspcfs.modules.admin.base.PermissionList" scope="request"/>
<body onLoad="javascript:document.roleForm.role.focus();">
<form name="roleForm" action="Roles.do?command=InsertRole&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<dhv:label name="admin.addRole">Add Role</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="button.add">Add</dhv:label>" name="Save">
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Roles.do?command=ListRoles'">
<br>
<dhv:formMessage />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="admin.addRole">Add Role</dhv:label></strong>
	  </th>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="admin.roleName">Role Name</dhv:label></td>
    <td><input type="text" name="role" maxlength="80" value="<%= toHtmlValue(Role.getRole()) %>"><font color="red">*</font> <%= showAttribute(request, "roleError") %></td>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></td>
    <td nowrap><input type="text" name="description" size="60" maxlength="255" value="<%= toHtmlValue(Role.getDescription()) %>"><font color="red">*</font> <%= showAttribute(request, "descriptionError") %></td>
  </tr>
<%--
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_portal_include.PortalRole">Portal Role</dhv:label>
    </td>
    <td>
    <input type="checkbox" name="roleType" value="on" />
    </td>
  </tr>
--%>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="5">
	    <strong><dhv:label name="admin.configurePermissions.text">Configure permissions for this role</dhv:label></strong>
	  </th>
  </tr>
<%
  Iterator i = PermissionList.iterator();
  int idCount = 0;
  while (i.hasNext()) {
    ++idCount;
    Permission thisPermission = (Permission)i.next();
    if (PermissionList.isNewCategory(thisPermission.getCategoryName())) {
%>
    <tr bgcolor="#E5E5E5">
      <td>
        <%= toHtml(thisPermission.getCategoryName()) %>
      </td>
      <td width="40" align="center"><dhv:label name="admin.accessView" param="break=<br />">Access/<br />View</dhv:label></td>
      <td width="40" align="center"><dhv:label name="button.add">Add</dhv:label></td>
      <td width="40" align="center"><dhv:label name="button.edit">Edit</dhv:label></td>
      <td width="40" align="center"><dhv:label name="button.delete">Delete</dhv:label></td>
  </tr>
<%
   }
%>  
  <tr>
    <td>
      <input type="hidden" name="permission<%= idCount %>id" value="<%= thisPermission.getId() %>">
      <%= toHtml(thisPermission.getDescription()) %>
    </td>
    <td align="center">
      <% if(thisPermission.getView()){ %>
      <input type="checkbox" value="ON" name="permission<%= idCount %>view">
      <% } else{ %>
      --
    <% } %>
    </td>
    <td align="center">
    <% if(thisPermission.getAdd()){ %>
      <input type="checkbox" value="ON" name="permission<%= idCount %>add">
    <% } else{ %>
      --
    <% } %>
    </td>
    <td align="center">
    <% if(thisPermission.getEdit()){ %>
      <input type="checkbox" value="ON" name="permission<%= idCount %>edit">
    <% } else{ %>
      --
    <% } %>
    </td>
    <td align="center">
    <% if(thisPermission.getDelete()){ %> 
      <input type="checkbox" value="ON" name="permission<%= idCount %>delete">
    <% }else{ %>
      --
    <% } %>
    </td>
  </tr>
<%
  }
%>
</table>
<br>
<input type="submit" value="<dhv:label name="button.add">Add</dhv:label>" name="Save">
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Roles.do?command=ListRoles'">
</form>
</body>
