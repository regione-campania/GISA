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
  - Version: $Id: mycfs_inbox_details.jsp 24345 2007-12-09 15:22:23Z srinivasar@cybage.com $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.mycfs.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="NoteDetails" class="org.aspcfs.modules.mycfs.base.CFSNote" scope="request"/>
<jsp:useBean id="InboxInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
<a href="MyCFSInbox.do?command=Inbox"><dhv:label name="Mailbox">Mailbox</dhv:label></a> >
<dhv:label name="accounts.MessageDetails">Message Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<!--
<dhv:evaluate if='<%= !InboxInfo.getListView().equalsIgnoreCase("sent") %>'>
<input type="button" name="btn" value="<dhv:label name="project.reply">Reply</dhv:label>" onClick="javascript:window.location.href='MyCFSInbox.do?command=ReplyToMessage&id=<%=NoteDetails.getId()%>&forwardType=<%= Constants.CFSNOTE %>'">
<% if(NoteDetails.getStatus() != 2) {%>
  <input type="button" value="<dhv:label name="accounts.accounts_list_menu.Archive">Archive</dhv:label>" onClick="javascript:window.location.href='MyCFSInbox.do?command=CFSNoteTrash&id=<%= NoteDetails.getId() %>&type=<%= NoteDetails.getType() %>';">
<%} else {%>
  <input type="button" value="<dhv:label name="calendar.sendToInbox">Send to Inbox</dhv:label>" onClick="javascript:window.location.href='MyCFSInbox.do?command=CFSNoteTrash&id=<%= NoteDetails.getId() %>&type=<%= NoteDetails.getType() %>';">
<%}%>
</dhv:evaluate>-->
<!-- <input type="button" name="btn" value="<dhv:label name="accounts.accounts_calls_list_menu.Forward">Forward</dhv:label>" onClick="javascript:window.location.href='MyCFSInbox.do?command=ForwardMessage&forwardType=<%= Constants.CFSNOTE %>&id=<%=NoteDetails.getId()%>'">
<input type="button" name="btn" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:confirmDelete('MyCFSInbox.do?command=CFSNoteDelete&id=<%= NoteDetails.getId() %>&type=<%= NoteDetails.getType() %>');">
-->
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><%= toHtml(NoteDetails.getSubject()) %></strong> 
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="campaign.from">From</dhv:label>
    </td>
    <td>
      <%= toHtml(NoteDetails.getSentName()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="calendar.received">Received</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= NoteDetails.getEntered() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="campaign.text">Text</dhv:label>
    </td>
    <td>
      <%= toHtml(NoteDetails.getBody()) %>
    </td>
  </tr>
</table>
<br>
<!--<dhv:evaluate if='<%= !InboxInfo.getListView().equalsIgnoreCase("sent") %>'>
<input type="button" name="btn" value="<dhv:label name="project.reply">Reply</dhv:label>" onClick="javascript:window.location.href='MyCFSInbox.do?command=ReplyToMessage&id=<%=NoteDetails.getId()%>&forwardType=<%= Constants.CFSNOTE %>'">
 <%// if(NoteDetails.getStatus() != 2) {%>
  <input type="button" value="<dhv:label name="accounts.accounts_list_menu.Archive">Archive</dhv:label>" onClick="javascript:window.location.href='MyCFSInbox.do?command=CFSNoteTrash&id=<%= NoteDetails.getId() %>&type=<%= NoteDetails.getType() %>';">
<%//} else {%>
  <input type="button" value="<dhv:label name="calendar.sendToInbox">Send to Inbox</dhv:label>" onClick="javascript:window.location.href='MyCFSInbox.do?command=CFSNoteTrash&id=<%= NoteDetails.getId() %>&type=<%= NoteDetails.getType() %>';">
<%//}%> 
</dhv:evaluate>-->
<!--<input type="button" name="btn" value="<dhv:label name="accounts.accounts_calls_list_menu.Forward">Forward</dhv:label>" onClick="javascript:window.location.href='MyCFSInbox.do?command=ForwardMessage&forwardType=<%= Constants.CFSNOTE %>&id=<%=NoteDetails.getId()%>'">
<input type="button" name="btn" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:confirmDelete('MyCFSInbox.do?command=CFSNoteDelete&id=<%= NoteDetails.getId() %>&type=<%= NoteDetails.getType() %>');">
-->
