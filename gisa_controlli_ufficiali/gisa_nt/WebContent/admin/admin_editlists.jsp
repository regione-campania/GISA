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
  - Version: $Id: admin_editlists.jsp 11310 2005-04-13 20:05:00Z mrajkowski $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.Iterator,org.aspcfs.modules.admin.base.PermissionCategory, org.aspcfs.utils.web.LookupList,org.aspcfs.utils.web.LookupListElement" %>
<jsp:useBean id="LookupLists" class="org.aspcfs.utils.web.LookupListList" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="35" align="center">
      &nbsp;
    </th>
    <th width="200">
      <strong><dhv:label name="admin.listName">List Name</dhv:label></strong>
    </th>
    <th width="35" align="center">
      <strong><dhv:label name="project.items">Items</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="button.preview">Preview</dhv:label></strong>
    </th>
  </tr>
<%
  int rowid = 0;
  Iterator i = LookupLists.iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1 ? 1 : 2);
    LookupListElement thisElement = (LookupListElement)i.next();
%>
    <tr class="row<%= rowid %>">
      <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="Admin.do?command=ModifyList&table=<%=thisElement.getTableName() %>&module=<%= thisElement.getModuleId() %>&sublist=<%= thisElement.getLookupId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Edit">Edit</dhv:label></a></td></dhv:permission>
      <td valign="center" width="200"><%= toHtml(thisElement.getTableName()) %></td>
      <td width="35" valign="center" align="center"><%= thisElement.getLookupList().getEnabledElementCount() %></td>
      <td valign="center"><%= thisElement.getLookupList().getHtmlSelect("list", 0) %></td>
    </tr>
<%
     }
%>
<dhv:evaluate if="<%= LookupLists.size() == 0 %>">
  <tr>
    <td valign="center" colspan="4"><dhv:label name="admin.noCustomLookupLists.text">No custom lookup lists to configure.</dhv:label></td>
  </tr>
</dhv:evaluate>
</table>
