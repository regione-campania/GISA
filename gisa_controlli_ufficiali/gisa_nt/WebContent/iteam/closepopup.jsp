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
  - Version: $Id: closepopup.jsp 11310 2005-04-13 20:05:00Z mrajkowski $
  - Description: 
  --%>
<html>
<%
  boolean scrollReload = false;
  String location = null;
  String returnPage = (String) request.getAttribute("return");
  if (returnPage == null) {
    returnPage = (String) request.getParameter("return");
  }
  String param = (String) request.getAttribute("param");
  if (param == null) {
    param = request.getParameter("param");
  }
  String param2 = (String) request.getAttribute("param2");
  if (param2 == null) {
    param2 = request.getParameter("param2");
  }
  if (returnPage != null && !"null".equals(returnPage)) {
    if ("ProjectEnterpriseView".equals(returnPage)) {
      location = "ProjectManagement.do?command=EnterpriseView";
      scrollReload = true;
    } else if ("ProjectPersonalView".equals(returnPage)) {
      location = "ProjectManagement.do?command=PersonalView";
      scrollReload = true;
    } else if ("ProjectRequirements".equals(returnPage)) {
      location = "ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=" + param;
      scrollReload = true;
    } else if ("ProjectAssignments".equals(returnPage)) {
      location = "ProjectManagement.do?command=ProjectCenter&section=Assignments&pid=" + param + "&rid=" + param2;
      scrollReload = true;
    } else if ("ProjectLists".equals(returnPage)) {
      location = "ProjectManagement.do?command=ProjectCenter&section=Lists&pid=" + param + "&cid=" + param2;
      scrollReload = true;
    } else if ("ProjectFiles".equals(returnPage)) {
      location = "ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=" + param + "&folderId=" + param2;
      scrollReload = true;
    } else {
      location = returnPage;
    }
  }
  
  if (location == null) {
%>
<body onload="window.opener.document.location.href='ProjectManagement.do'; window.close();">
<%
  } else if ("Calendar".equalsIgnoreCase(returnPage)) {
%>
<body onload="window.opener.parent.reloadFrames(); window.close();">
<% 
  } else if (scrollReload) {
%>
<body onload="window.opener.scrollReload('<%= location %>'); window.close();">
<%  
  } else {
%>
<body onload="window.opener.location='<%= location %>'; window.close();">
<%
  }
%>
</body>
</html>
