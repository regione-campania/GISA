<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Dialog" class="org.aspcfs.utils.web.HtmlDialog" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<%@ include file="initPage.jsp" %>
<jsp:include page="templates/cssInclude.jsp" flush="true"/>
<body id="body">
  <%= Dialog.getFrameHtml(1) %>
<% 
  if (Dialog.getSynchFrameCounter() == 0) {
    session.removeAttribute("Dialog");
  }
%>
</body>

