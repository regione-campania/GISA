<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,com.zeroio.iteam.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="form" class="java.lang.String" scope="request"/>
<jsp:useBean id="selected" class="java.lang.String" scope="request"/>
<jsp:useBean id="obj" class="java.lang.String" scope="request"/>
<jsp:useBean id="stateObj" class="java.lang.String" scope="request"/>
<jsp:useBean id="stateSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<body onLoad="setStates();">
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function newOpt(param, value) {
    var newOpt = parent.document.createElement("OPTION");
    newOpt.text=param;
    newOpt.value=value;
    return newOpt;
  }

  function setStates() {
<%  
    if (stateSelect.size() == 0) { %>
      parent.continueUpdateState('<%= obj %>', 'true');
<%  } else { %>
      parent.continueUpdateState('<%= obj %>', 'false');
      var stateSelect = parent.document.getElementById('<%= stateObj %>');
      stateSelect.options.length = 0;
      var selectedOption = -1;
      stateSelect.options[stateSelect.options.length] = newOpt(label('option.none','-- None --'),'-1');
<%    Iterator iter = (Iterator) stateSelect.iterator();
      boolean foundSelected = false;
      while (iter.hasNext()) {
        HtmlOption option = (HtmlOption) iter.next();
        if (selected.equals(option.getValue())) {
          foundSelected = true;
%>
        selectedOption = stateSelect.options.length;
<%      } %>
        stateSelect.options[stateSelect.options.length] = newOpt('<%= option.getText() %>', '<%= option.getValue() %>');
<%    }
      if (!foundSelected && selected != null && !"".equals(selected) && !"null".equals(selected) && !"-1".equals(selected)) { %>
      selectedOption = stateSelect.options.length;
      stateSelect.options[stateSelect.options.length] = newOpt('<%= selected %> *', '<%= selected %>');
<%    } %>
      if (selectedOption > -1) {
        stateSelect.options.selectedIndex = selectedOption;
      }
<%  } %>
  }
</script>
</body>