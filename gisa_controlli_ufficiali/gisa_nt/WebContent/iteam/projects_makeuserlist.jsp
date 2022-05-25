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
  - Version: $Id: projects_makeuserlist.jsp 15115 2006-05-31 16:47:51Z matt $
  - Description: 
  --%>
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*,org.aspcfs.utils.StringUtils" %>
<%@ page import="java.sql.*" %>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<html>
<head>
</head>
<body onload='page_init();'>
<script language='Javascript'>
function page_init() {
  var list = parent.document.forms['projectMemberForm'].elements['selTotalList'];
  list.options.length = 0;
<%
  Iterator i = UserList.iterator();
  while (i.hasNext()) {
    User thisUser = (User)i.next();
    Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
    String tempString = "";
    if ((thisUser.getExpires() != null && currentTime.after(thisUser.getExpires())) || !thisUser.getEnabled()) {
      tempString = " *";
    }
%>
  if ( !(inArray(parent.document.forms['projectMemberForm'].elements['selProjectList'], <%= thisUser.getId() %>)) ) {
    var newOpt = parent.document.createElement("OPTION");
    newOpt.text="<%= StringUtils.jsStringEscape(thisUser.getContact().getNameFirstLast()+tempString) %>";
    newOpt.value='<%= thisUser.getId() %>';
    if (newOpt.value != '0') {
      list.options[list.length] = newOpt;
    }
  }
  parent.initList(<%= thisUser.getId() %>);
<%
  }
%>
}

function inArray(a, s) {
	var i = 0;
	for(i=0; i < a.length; i++) {
		if (a.options[i].value == s) {
			return true;
		}
	}
	return false;
}
</script>
</body>
</html>

