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
  - Version: $Id: document_store_enterpriseview.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.documents.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="documentStoreList" class="org.aspcfs.modules.documents.base.DocumentStoreList" scope="request"/>
<jsp:useBean id="documentStoreListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="DocumentManagementAsl.do">Elenco Reperibilita Asl</a> >
<dhv:label name="project.list">List</dhv:label>
</td>
</tr>
</table>
<%-- End trails --%>


<%-- Show the document stores --%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="20%" nowrap>
      <strong><dhv:label name="documents.details.title">Title</dhv:label></strong>
    </th>
    <th width="50%" >
      <strong><dhv:label name="documents.details.shortDescription">Short Description</dhv:label></strong>
    </th>
    <th width="30%" >
      <strong><dhv:label name="documents.details.status">Status</dhv:label></strong>
    </th>
  </tr>
<%
  int rowid = 0;
  Iterator itr = documentStoreList.iterator();
  if (itr.hasNext()) {
    String tableHighlight1 = "#000000";
    String tableHighlight2 = "#0000FF";
    String currentHighlight = tableHighlight2;
    while (itr.hasNext()) {
      if (currentHighlight.equals(tableHighlight1)) {
        currentHighlight = tableHighlight2;
      } else {
        currentHighlight = tableHighlight1;
      }
      DocumentStore thisDocumentStore = (DocumentStore) itr.next();
      rowid = (rowid != 1 ? 1 : 2);
%>
    <tr class="row<%= rowid %>">
      <td valign="top">
        <a href="DocumentManagementAsl.do?command=DocumentStoreCenter&documentStoreId=<%= thisDocumentStore.getId() %>"><b><%= toHtml(thisDocumentStore.getTitle()) %></b></a>
      </td>
      <td valign="top">
        <%= toHtml(thisDocumentStore.getShortDescription()) %>
      </td>
      <td>
      <dhv:evaluate if="<%= thisDocumentStore.getCloseDate() == null && thisDocumentStore.getApprovalDate() == null %>">
        <img border="0" src="images/box-hold.gif" alt="<dhv:label name='alt.onHold'>On Hold</dhv:label>" align="absmiddle">
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisDocumentStore.getCloseDate() != null %>">
        <font color="blue">
        <dhv:label name="documents.details.archivedMessage" param='<%= "time="+getTime(pageContext,thisDocumentStore.getCloseDate(),User.getTimeZone(),DateFormat.SHORT,true,false,false,"&nbsp;") %>'>This document store was archived on&nbsp;<zeroio:tz timestamp="<%= thisDocumentStore.getCloseDate() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label>
        </font>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisDocumentStore.getCloseDate() == null %>">
        <dhv:evaluate if="<%= thisDocumentStore.getApprovalDate() == null %>">
          <font color="red"><dhv:label name="documents.details.unapprovedMessage">This document store is currently under review and has not been approved</dhv:label></font>
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisDocumentStore.getApprovalDate() != null %>">
          <font color="darkgreen"><dhv:label name="documents.details.approvedMessage" param='<%= "time="+getTime(pageContext,thisDocumentStore.getApprovalDate(),User.getTimeZone(),DateFormat.SHORT,true,false,false,"&nbsp;")  %>'>This document store was approved on&nbsp; <zeroio:tz timestamp="<%= thisDocumentStore.getApprovalDate() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label></font>
        </dhv:evaluate>
      </dhv:evaluate>
      </td>
    </tr>
<%      
      }
    }else {
%>
  <tr>
    <td colspan="3">
      <dhv:label name="documents.enterprise.noDocumentStoresMessage">No document stores to display.</dhv:label>
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<dhv:pagedListControl object="documentStoreListInfo"/>