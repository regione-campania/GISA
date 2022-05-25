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
  - Version: $Id: mycfs_newmessage.jsp 15627 2006-08-08 19:08:07Z matt $
  - Description:
  --%>
<%-- Trails --%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
<a href="Messaggi.do?command=Inbox&return=1"><dhv:label name="myitems.messaggi">Messaggi</dhv:label></a> >
<dhv:label name="actionList.nuovoMessaggioa">Nuovo Messaggio</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<script type="text/javascript">
  function hideSendButton() {
    try {
      var send1 = document.getElementById('send1');
      send1.value = label('label.sending','Sending...');
      send1.disabled=true;
    } catch (oException) {}
    try {
      var send2 = document.getElementById('send2');
      send2.value = label('label.sending','Sending...');
      send2.disabled=true;
    } catch (oException) {}
  }
</script>
<form name="newMessageForm" action="Messaggi.do?command=SendMessageDocumentale" method="post" onSubmit="return sendMessage();">
<input type="submit" id="send1" value="<dhv:label name="global.button.send">Send</dhv:label>" />
<input type="button" id="cancel1" value="Annulla" onClick="javascript:window.location.href='Messaggi.do?command=Inbox'"><br><br>
<%@ include file="../messaggionuovo_include.jsp" %>
<br>
<input type="submit" id="send2" value="<dhv:label name="global.button.send">Send</dhv:label>" />
<input type="button" id="cancel2" value="Annulla" onClick="javascript:window.location.href='Messaggi.do?command=Inbox'">
</form>

