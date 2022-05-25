<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="java.util.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,org.aspcfs.modules.anagrafe_animali.base.*"%>


<jsp:useBean
	id="veterinariList"
	class="org.aspcfs.modules.admin.base.UserList"
	scope="request" />
<jsp:useBean id="veterinariListInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="request" />
<jsp:useBean id="countRegolarizzati"
	class="java.lang.String" scope="request" />


<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />



<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>

<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
		
			<td><dhv:label
						name="">Lista LLPP</dhv:label></a></td>

		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>



<dhv:pagedListStatus title='<%=showError(request, "actionError")%>'
	object="veterinariListInfo" />

<br />







<table class="details" cellpadding="4" cellspacing="0" border="0"
	width="100%">
	<thead>
		<tr>
			<th width="16%" nowrap><strong>Nome</strong></th>
			<th width="16%" nowrap><strong><dhv:label name="">Cognome</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Username</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Email</dhv:label></strong>
			</th>
			<th width="16%"><strong><dhv:label name="">Telefono</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Ruolo</dhv:label></strong>
			</th>
		</tr>
	</thead>
	<tbody>
		<%
			Iterator itr = veterinariList.iterator();
			
			
			if (itr.hasNext()) {
				int rowid = 0;
				int i = 0;
				while (itr.hasNext()) {
					i++;
					rowid = (rowid != 1 ? 1 : 2);
					User thisUser = (User) itr.next();
					Contact thisContact = thisUser.getContact();
		
		%>
		<tr class="row<%=rowid%>">
			<td width="15%" nowrap><%=toHtml(thisContact.getNameFirst())%></td>
			<td width="15%" nowrap><%=toHtml(thisContact.getNameLast())%></td>
			<td width="15%" nowrap><%=toHtml(thisUser.getUsername())%></td>
			<td width="15%" nowrap><%=toHtml(thisContact.getPrimaryEmailAddress()) %></td>
			<td width="15%" nowrap><%=toHtml(thisContact.getPrimaryPhoneNumber()) %></td>
			<td width="15%" nowrap><%=thisUser.getRole()%>&nbsp;</td>
		</tr>
		<%
			}
			} else {
				%>
				
						
			   <tr class="containerBody">
		<td colspan="9"><dhv:label name="">Non sono stati trovati LLPP.</dhv:label>
		</td>
	</tr>
			    <%
			    
		    
	

		}%>
	</tbody>
</table>Soggetti regolarizzati: <%=countRegolarizzati %>
<dhv:pagedListControl object="veterinariListInfo" />