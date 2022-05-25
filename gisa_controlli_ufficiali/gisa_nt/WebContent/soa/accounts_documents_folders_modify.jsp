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
  - Version: $Id: accounts_documents_folders_modify.jsp 11310 2005-04-13 20:05:00Z mrajkowski $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.modules.soa.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.soa.base.Organization" scope="request"/>
<jsp:useBean id="FileFolder" class="com.zeroio.iteam.base.FileFolder" scope="request"/>
<jsp:useBean id="folderHierarchy" class="com.zeroio.iteam.base.FileFolderHierarchy" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="0" cellspacing="0" width="100%" border="0">
<tr>
  <td>
    <dhv:label name="soa.soa_documents_file_move.SelectFolderToMove">Select a folder to move the item to</dhv:label>:<br />
    <img src="images/icons/stock_folder-16-19.gif" border="0" align="absmiddle" />
    <%= toHtml(FileFolder.getSubject()) %>
  </td>
</tr>
</table>
&nbsp;<br>
<table cellpadding="0" cellspacing="0" width="100%" border="1" rules="cols">
  <tr class="section">
    <td valign="top" width="100%">
      <img alt="" src="images/tree7o.gif" border="0" align="absmiddle" height="16" width="19"/>
      <img alt="" src="images/icons/stock_open-16-19.gif" border="0" align="absmiddle" height="16" width="19"/>
    <dhv:evaluate if="<%= FileFolder.getParentId() != -1 %>">
      <a href="AccountsDocumentsFoldersSoa.do?command=SaveMove&orgId=<%= OrgDetails.getOrgId() %>&id=<%= FileFolder.getId() %>&popup=true&folderId=0&return=AccountsDocumentsSoa&param=<%= OrgDetails.getOrgId() %>&param2=<%= FileFolder.getParentId() %>"><dhv:label name="soa.soa_documents_file_move.Home">Home</dhv:label></a>
    </dhv:evaluate>
    <dhv:evaluate if="<%= FileFolder.getParentId() == -1 %>">
      <dhv:label name="soa.soa_documents_file_move.Home">Home</dhv:label>
      <dhv:label name="soa.soa_documents_file_move.CurrentFolder">(current folder)</dhv:label>
    </dhv:evaluate>
    </td>
  </tr>
<%
  int rowid = 0;
  Iterator i = folderHierarchy.getHierarchy().iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1?1:2);
    FileFolder thisFolder = (FileFolder) i.next();
%>    
  <tr class="row<%= rowid %>">
    <td valign="top">
      <img border="0" src="images/treespace.gif" align="absmiddle" height="16" width="19">
      <img alt="" src="images/tree7o.gif" border="0" align="absmiddle" height="16" width="19"/>
      <img border="0" src="images/icons/stock_open-16-19.gif" align="absmiddle">
    <dhv:evaluate if="<%= FileFolder.getParentId() != thisFolder.getId() %>">  
      <a href="AccountsDocumentsFoldersSoa.do?command=SaveMove&orgId=<%= OrgDetails.getId() %>&id=<%= FileFolder.getId() %>&popup=true&folderId=<%= thisFolder.getId() %>&return=AccountsDocumentsSoa&param=<%= OrgDetails.getOrgId() %>&param2=<%= FileFolder.getParentId() %>"><%= toHtml(thisFolder.getSubject()) %></a>
    </dhv:evaluate>
    <dhv:evaluate if="<%= FileFolder.getParentId() == thisFolder.getId() %>">
      <%= toHtml(thisFolder.getSubject()) %>
      <dhv:label name="soa.soa_documents_file_move.CurrentFolder">(current folder)</dhv:label>
    </dhv:evaluate>
    </td>
  </tr>
<%
  }
%>
</table>
&nbsp;<br />
<input type="button" value="Annulla" onClick="javascript:window.close()" />
