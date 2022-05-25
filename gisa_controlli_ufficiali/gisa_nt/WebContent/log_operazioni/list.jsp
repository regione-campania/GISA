<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.* ,org.aspcfs.modules.admin.base.UserOperation"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<jsp:useBean id="op_list" class="org.aspcfs.modules.logOperazioni.LogOperazioniList" scope="request"/>
<jsp:useBean id="searchUsername" class="java.lang.String" scope="request"/>
<jsp:useBean id="searchtimestampDateStart" class="java.lang.String" scope="request"/>
<jsp:useBean id="searchtimestampDateEnd" class="java.lang.String" scope="request"/>

<dhv:pagedListStatus title=''	object="op_list" />

<table class="trails" cellspacing="0">
	<tr>
		<td width="20%"><a href="LogOperazioni.do?command=Home"><dhv:label
			name="">Log Operazioni</dhv:label></a> > <dhv:label name="">Lista Operazioni</dhv:label>
		</td>
	</tr>
</table>

<table class="details" cellpadding="4" cellspacing="0" border="0"
	width="100%">
	<thead>
		<tr>
			<th width="11%" nowrap><strong>ID</strong></th>
			<th width="16%" nowrap><strong>DATA</strong></th>
			<th width="16%" nowrap><strong><dhv:label name="">Username</dhv:label></strong></th>
			<th width="51%" style="text-overflow: ellipsis; overflow: hidden;" nowrap><strong><dhv:label name="">URL</dhv:label></strong></th>
		</tr>
	</thead>
	<tbody>
<%	Iterator itr = op_list.iterator();
	if (itr.hasNext()) {
		int rowid = 0;
		int i = 0;
		while (itr.hasNext()) {
			i++;
			rowid = (rowid != 1 ? 1 : 2);
			UserOperation op = (UserOperation) itr.next(); %>
			<tr class="row<%=rowid%>">
				<td width="10%" nowrap><a href="LogOperazioni.do?command=Details&idOp=<%=op.getId()%>&searchUsername=<%=searchUsername%>&searchtimestampDateStart=<%=searchtimestampDateStart%>&searchtimestampDateEnd=<%=searchtimestampDateEnd%>"><%=op.getId()%></a></td>
				<td width="15%" nowrap><%=op.getData()%></td>
				<td width="15%" nowrap><%=op.getUsername()%></td>
				<td width="50%" style="text-overflow: ellipsis; overflow: hidden;"><%=op.getUrl()%></td>
			</tr>
	  <%}
	} else { %>				
			<tr class="containerBody">
				<td colspan="9"><dhv:label name="">Non sono state trovate operazioni.</dhv:label></td>
			</tr>
<% }%>
	</tbody>
</table>

<dhv:pagedListControl object="op_list" />