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
  - Author(s): 
  - Version: $Id: document_store_center_file_library.jsp 12404 2005-08-05 17:37:07Z mrajkowski $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*, org.aspcfs.modules.documents.base.*" %>

<%@page import="org.aspcfs.modules.documents.base.FileItemList"%>
<%@page import="com.zeroio.iteam.base.FileItem"%>
<jsp:useBean id="documentStoreUserTeamInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>

<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="fileFolderList" class="com.zeroio.iteam.base.FileFolderList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="document_store_center_files_menu.jsp" %>
<%-- Preload image rollovers for drop-down menu --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select_<%= SKIN %>');
</script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%
UserBean user = (UserBean)session.getAttribute("User");


%>

<dhv:evaluate if="<%= !documentStore.isTrashed() %>" >
<dhv:evaluate if="<%= documentStore.getSiteId()==user.getSiteId()|| user.getSiteId()==-1%>" >
<dhv:permission name="documentcenter-documents-files-asl-upload-add">
<img src="images/icons/stock_insert-file-16.gif" border="0" align="absmiddle" />
<a href="DocumentStoreManagementFilesAsl.do?command=Add&documentStoreId=<%= documentStore.getId() %>&folderId=<%= documentStore.getFiles().getFolderId() %>">Allega lista Reperibilita</a>
</dhv:permission>
</dhv:evaluate>

</dhv:evaluate>
<br/><br/>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="documentStoreUserTeamInfo"/>

<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    
    <th width="100%"><strong><dhv:label name="documents.documents.file">File</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="documents.documents.type">Type</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="documents.documents.size">Size</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="documents.documents.version">Version</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="documents.documents.dateModified">Date Modified</dhv:label></strong></th>
  </tr>
<dhv:evaluate if="<%= documentStore.getFiles().size() == 0 && fileFolderList.size() == 0 %>">
  <tr class="row2">
    <td colspan="6"><dhv:label name="documents.documents.noFilesMessage">No files to display.</dhv:label></td>
  </tr>
</dhv:evaluate>
<%
  int rowid = 0;
  
  FileItemList files = (FileItemList)documentStore.getFiles();
  Iterator i = files.iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1?1:2);
    FileItem thisFile = (FileItem)i.next();
   
%>    
  <tr class="row<%= rowid %>">
  
    <td width="100%">
      <%= thisFile.getImageTag("-23") %>
      <a href="DocumentStoreManagementFilesAsl.do?command=Details&documentStoreId=<%= documentStore.getId() %>&fid=<%= thisFile.getId() %>&folderId=<%= documentStore.getFiles().getFolderId() %>"><%= toHtml(thisFile.getSubject()) %></a>
    </td>
    <td align="center" nowrap><%= toHtml(thisFile.getExtension()) %></td>
    <td align="right" nowrap>
      <%= thisFile.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
    </td>
    <td align="center" nowrap>
      <%= thisFile.getVersion() %>&nbsp;
    </td>
    <td align="center" nowrap>
      <zeroio:tz timestamp="<%= thisFile.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/><br />
      <dhv:username id="<%= thisFile.getModifiedBy() %>"/>
    </td>
  </tr>
<%
  }
%>
</table>
