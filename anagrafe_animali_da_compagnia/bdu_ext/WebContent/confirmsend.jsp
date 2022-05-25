<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*" %>
<jsp:useBean id="InboxInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="finalContacts" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="result" class="java.lang.String" scope="request"/>
<%@ include file="initPage.jsp" %>
<p>
<!-- >table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th>
      <dhv:label name="calendar.messageSentConfirmation.text">Your message has been sent to the following recipients:</dhv:label>
    </th>
  </tr-->

  <dhv:evaluate if="<%=("1").equals(result) %>">
  	<dhv:label name="calendar.messageSentConfirmation.text"></dhv:label>
  </dhv:evaluate>
  <dhv:evaluate if="<%=("-1").equals(result) %>">
  	<dhv:label name="calendar.messageSentErrorConfirmation.text"></dhv:label>
  </dhv:evaluate>
<%--
	Set s = finalContacts.keySet();
	Iterator i = s.iterator();
	int rowid = 0;
	while (i.hasNext()) {
		rowid = (rowid != 1?1:2);
    Integer hashKey = (Integer) i.next();
    int contactId = hashKey.intValue();
		Object st = finalContacts.get(hashKey);
		String email = st.toString();
		if(email.startsWith("P:")) {
			email = email.substring(2);
		}
%>
  <tr class="row<%= rowid %>">
    <td>
      <%= toHtml(email) %> <%= showAttribute(request, "contact" + contactId) %>
    </td>
  </tr>
<%
	}
	session.removeAttribute("finalContacts");
  session.removeAttribute("selectedContacts");
--%>
</table>
