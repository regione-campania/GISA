<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.campioni.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %><html>
<jsp:useBean id="input" class="java.lang.String" scope="request"/>
<jsp:useBean id="id" class="java.lang.String" scope="request"/>
<jsp:useBean id="orgId" class="java.lang.String" scope="request"/>

<script type="text/javascript">
function ViewDetails(){
	document.campioni_dettaglio.action='<%=request.getParameter("input")%>?command=TicketDetails&id=<%=request.getParameter("id")%>&orgId=<%=request.getParameter("orgId")%>';
	document.campioni_dettaglio.submit();
}
</script>

<form name="campioni_dettaglio" action="" method="post">
</form>

<script type="text/javascript">
	ViewDetails();
</script>