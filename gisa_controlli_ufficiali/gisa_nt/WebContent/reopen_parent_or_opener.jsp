<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="id" class="java.lang.String" scope="request"/>
<body onLoad="forwardLink();">
<%@ include file="initPage.jsp" %>
<script type="text/javascript">
  function forwardLink(){
    if ('<%= !isPopup(request) %>' == 'true') {
      if ('<%= (id != null && !"".equals(id)) %>' == 'true') {
        var link = parent.reopenId('<%= id %>');
      } else {
        var link = parent.reopen();
      }
    } else {
      if ('<%= (id != null && !"".equals(id)) %>' == 'true') {
        var link = opener.reopenId('<%= id %>');
      } else {
        var link = opener.reopen();
      }
      self.close();
    }
  }
</script>
</body>
