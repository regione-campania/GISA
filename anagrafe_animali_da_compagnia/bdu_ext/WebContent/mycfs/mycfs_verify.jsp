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
  - Version: $Id: mycfs_verify.jsp 24345 2007-12-09 15:22:23Z srinivasar@cybage.com $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Session" class="org.aspcfs.controller.UserSession" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></script>
<%@ include file="../initPage.jsp" %>

<%
System.out.println(request.getAttribute("access_position_lat"));
System.out.println(request.getAttribute("access_position_err"));
String lat =request.getAttribute("access_position_lat")+"";
String lon = request.getAttribute("access_position_lon")+"";
String err = request.getAttribute("access_position_err")+"";

%>


<script type="text/javascript">
  function loginAlert(ipAddress,lat,lon,err){
    if (confirm(
        label("check.user.twologins.one","This application only permits a user to be logged in once\n and it appears that you are already logged in from\n the following internet address: ") + 
        ipAddress + 
        label("check.user.twologins.two",".\n\n This message could also appear if you did not previously log out\n and you are simply trying to login again from the same browser.\n\n Choose OK to continue logging in.\n Choose CANCEL to return to the login screen."))) {
      window.location.href = 'Login.do?command=LoginConfirm&override=yes&access_position_lat='+lat+'&access_position_lon='+lon+'&access_position_err='+err;
    }else{
      window.location.href = 'Login.do?command=LoginConfirm&override=no';
    }
  }
</script>
<body onLoad="javascript:loginAlert('<%= Session.getIpAddress() %>','<%=lat%>','<%=lon%>','<%=err%>');">

