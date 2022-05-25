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
  - Version: $Id: global_addtask.jsp 24350 2007-12-09 15:28:13Z srinivasar@cybage.com $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*"%>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.addTask.description.focus();">
<form name="addTask" action="MyTasks.do?command=<%= Task.getId() != -1 ? "Update" : "Insert" %>&id=<%= Task.getId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>&actionSource=<%= request.getParameter("actionSource") %>&global=true" method="post" onSubmit="return validateTask();">
<% boolean popUp = request.getParameter("popup") != null; %>
<dhv:formMessage showSpace="false" />
<%@ include file="../tasks/task_include.jsp" %>
<br>
<% if (Task.getId() == -1) { %>
  <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>" />
<%} else {%>
<input type="submit" value="<dhv:label name="button.update">Update</dhv:label>"/>
<%}%>
<input type="button" value="Annulla" onClick="javascript:window.close();">
<br>
</form>
</body>

