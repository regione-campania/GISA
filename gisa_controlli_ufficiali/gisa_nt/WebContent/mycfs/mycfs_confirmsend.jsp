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
  - Version: $Id: mycfs_confirmsend.jsp 12404 2005-08-05 17:37:07Z mrajkowski $
  - Description: 
  --%>
<%-- Trails --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">La mia Home Page</dhv:label></a> >
<a href="MyCFSInbox.do?command=Inbox&return=1"><dhv:label name="myitems.mailbox">Mailbox</dhv:label></a> >
<% if("-1".equals(request.getParameter("noteId"))){ %>
<a href="javascript:window.location.href='MyCFSInbox.do?command=NewMessage&sendUrl='+escape('MyCFSInbox.do?command=SendMessage')+'&return='+escape('MyCFSInbox.do?command=Inbox');"><dhv:label name="actionList.newMessage">New Message</dhv:label></a><br>
<% } else { %>
<a href="MyCFSInbox.do?command=CFSNoteDetails&id=<%= request.getParameter("noteId") %>"><dhv:label name="accounts.MessageDetails">Message Details</dhv:label></a> >
<dhv:label name="calendar.forwardMessage">Forward Message</dhv:label>
<%}%>
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="../confirmsend.jsp" %>
