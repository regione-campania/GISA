<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id: projects_center_hierarchy.jsp 11310 2005-04-13 20:05:00Z mrajkowski $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="requirement" class="com.zeroio.iteam.base.Requirement" scope="request"/>
<jsp:useBean id="assignment" class="com.zeroio.iteam.base.Assignment" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="0" cellspacing="0" width="100%" border="0">
<tr>
  <td>
    <dhv:label name="accounts.accounts_documents_file_move.SelectFolderToMove">Select a folder to move the item to</dhv:label>:<br />
    <%= assignment.getStatusGraphicTag() %>
    <%= toHtml(assignment.getRole()) %>
  </td>
</tr>
</table>
<br />
<table cellpadding="0" cellspacing="0" width="100%" border="1" rules="cols">
<%
Requirement thisRequirement = requirement;
%>
  <tr class="section">
    <td valign="top" width="100%">
      <img alt="" src="images/tree7o.gif" border="0" align="absmiddle" height="16" width="19"/>
      <img alt="" src="images/folder1open.gif" border="0" align="absmiddle" height="16" width="19"/>
      <a href="ProjectManagementAssignments.do?command=SaveMove&pid=<%= project.getId() %>&rid=<%= requirement.getId() %>&aid=<%= assignment.getId() %>&popup=true&parent=0&return=ProjectAssignments&param=<%= project.getId() %>&param2=<%= requirement.getId() %>"><%= toHtml(thisRequirement.getShortDescription()) %></a>
    </td>
  </tr>
<%
    AssignmentFolder plan = thisRequirement.getPlan();
    Iterator planIterator = plan.getPlanIterator();
    HashMap treeStatus = new HashMap();
    int rowid = 0;
    while (planIterator.hasNext()) {
      rowid = (rowid != 1?1:2);
      Object planItem = (Object) planIterator.next();
      if (planItem instanceof AssignmentFolder) {
        //AssignmentFolders
        AssignmentFolder thisFolder = (AssignmentFolder) planItem;
%>
  <tr class="row<%= rowid %>">
    <td valign="top">
<%
      treeStatus.put(new Integer(0), new Boolean(false));
      treeStatus.put(new Integer(thisFolder.getDisplayLevel()), new Boolean(thisFolder.getLevelOpen()));
      for (int count = 0; count < thisFolder.getDisplayLevel() + 1; count++) {
        boolean folderOpen = ((Boolean) treeStatus.get(new Integer(count))).booleanValue();
%>
    <dhv:evaluate if="<%= folderOpen && count != thisFolder.getDisplayLevel() %>">  
      <img border="0" src="images/tree2.gif" align="absmiddle" height="16" width="19">
    </dhv:evaluate>
    <dhv:evaluate if="<%= folderOpen && count == thisFolder.getDisplayLevel() %>">  
      <img border="0" src="images/tree5o.gif" align="absmiddle" height="16" width="19">
    </dhv:evaluate>
    <dhv:evaluate if="<%= !folderOpen && count == thisFolder.getDisplayLevel() %>">  
      <img border="0" src="images/tree5o.gif" align="absmiddle" height="16" width="19">
    </dhv:evaluate>
    <dhv:evaluate if="<%= !folderOpen && count != thisFolder.getDisplayLevel() %>">
      <img border="0" src="images/treespace.gif" align="absmiddle" height="16" width="19">
    </dhv:evaluate>
<%    }   %>
      <img border="0" src="images/folder1open.gif" align="absmiddle" align="absmiddle" height="16" width="19">
      <a href="ProjectManagementAssignments.do?command=SaveMove&pid=<%= project.getId() %>&rid=<%= requirement.getId() %>&aid=<%= assignment.getId() %>&popup=true&parent=<%= thisFolder.getId() %>&return=ProjectAssignments&param=<%= project.getId() %>&param2=<%= requirement.getId() %>"><%= toHtml(thisFolder.getName()) %></a>
      <dhv:evaluate if="<%= assignment.getFolderId() == thisFolder.getId() %>">
      <dhv:label name="accounts.accounts_documents_file_move.CurrentFolder">(current folder)</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
<%
      }
  }
%>
</table>
<br />
<input type="button" value="Annulla" onClick="javascript:window.close()">
