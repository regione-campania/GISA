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
  - Version: $Id: document_store_center.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*, org.aspcfs.modules.documents.base.* " %>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="currentMember" class="org.aspcfs.modules.documents.base.DocumentStoreTeamMember" scope="request"/>
<jsp:useBean id="documentStoreView" class="java.lang.String" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="DocumentManagement.do"><dhv:label name="Documents" mainMenuItem="true">Documents</dhv:label></a> >
<a href="DocumentManagementAsl.do">Lista Reperibilita Asl</a> >
<dhv:label name="documents.documentStoreDetails">Document Store Details</dhv:label>
</td>
</tr>
</table>
<%-- End trails --%>
<%
  if (documentStore.getId() == -1) {
%>
<br /><font color="red"><dhv:label name="documents.documentStoreDoesNotExist">This document store does not belong to you, or does not exist!</dhv:label></font>
<%
  } else {
    String section = (String) request.getAttribute("IncludeSection");
    String includeSection = "document_store_center_" + section + ".jsp";
    
%>
<table border="0" width="100%">
  <tr>
    <td>
      <img src="images/icons/stock_navigator-open-toolbar-16.gif" border="0" align="absmiddle">
    </td>
    <td width="100%">
      <strong><%= toHtml(documentStore.getTitle()) %></strong>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="2">
      <%= toHtml(documentStore.getShortDescription()) %>
    </td>
  </tr>
</table>
<div class="tabs" id="toptabs">
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
        <zeroio:tabbedMenu text='<%= "ReperibiliaAsl" %>' display="ReperibiliaAsl" type="project.documents" key="file" value="ReperibilitaAsl" url= ""/>
    
    <td width="100%" style="background-image: none; background-color: transparent; border: 0px; border-bottom: 1px solid #666; cursor: default;">&nbsp;</td>
  </tr>
</table>
</div>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
    <% %>
      <jsp:include page="<%= includeSection %>" flush="true"/>
      <br>
    </td>
  </tr>
</table>
<br>


<%
  }
%>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
