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
  - Version: $Id: util_edit_list_closepopup.jsp 11310 2005-04-13 20:05:00Z mrajkowski $
  - Description: 
  --%>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.aspcfs.utils.web.HtmlOption" %>
<jsp:useBean id="editList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<html>
<script language="javascript" type="text/javascript">
  function updateParentList() {
    // Get handle to list
    var list = opener.document.forms['<%= request.getParameter("form") %>'].elements['<%= request.getParameter("field") %>'];
    // TODO: Get the currently selected value
    
    // Set the option count to 0
    list.options.length = 0;
<%
    Iterator i = editList.iterator();
    while (i.hasNext()) {
      HtmlOption thisOption = (HtmlOption) i.next();
%>
      // Add each option, determine if it was previously selected
      var newOpt = opener.document.createElement("OPTION");
      newOpt.text='<%= thisOption.getText() %>';
      newOpt.value='<%= thisOption.getValue() %>';
      list.options[list.length] = newOpt;
<%
    }
%>
  }
</script>
<body onload="updateParentList();window.close();">
Changes successful.
</body>
</html>
