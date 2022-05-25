<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*,org.aspcfs.utils.*" %>
<%@ page import="org.aspcfs.modules.assets.base.*" %>
<jsp:useBean id="selectedQtys" class="java.util.HashMap" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:init_page();">
<script type="text/javascript">
  function init_page() {
    var materials = '';
<%
  Iterator iter = (Iterator) selectedQtys.keySet().iterator();
  while (iter.hasNext()) {
    Integer key = (Integer) iter.next();
%>
    materials = materials+'<%= key.intValue()+","+ (selectedQtys.get(key) != null && !"".equals(((String) selectedQtys.get(key)).trim()) ? (String) selectedQtys.get(key):"0")+"|" %>';
<%}%>
    if ('<%= isPopup(request) %>'== 'true') {
      opener.resetMaterials(materials);
      self.close();
    } else {
      parent.resetMaterials(materials);
    }
  }
</script>
</body>
