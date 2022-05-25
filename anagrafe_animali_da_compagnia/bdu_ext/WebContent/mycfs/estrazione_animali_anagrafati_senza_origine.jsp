<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,org.aspcfs.modules.anagrafe_animali.base.*"%>


<jsp:useBean id="lista" class="java.util.ArrayList" scope="request" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />



<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>

<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
		
			<td><dhv:label
						name="">Lista Veterinari e animali anagrafati senza origine</dhv:label></a></td>

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
			<th width="16%" nowrap><strong>User Id</strong></th>
			<th width="16%" nowrap><strong><dhv:label name="">Username</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Nominativo</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Ruolo</dhv:label></strong>
			</th>
			<th width="16%"><strong><dhv:label name="">Asl</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Provincia Iscrizione</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Luogo</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Anagrafati senza origine</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">meticci</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">di razza</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Anagrafati con origine</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">meticci</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">di razza</dhv:label></strong>
			</th>
			
			<th width="16%" nowrap><strong><dhv:label name="">Totale anagrafati</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Percentuale senza origine</dhv:label></strong>
			</th>
		</tr>
	</thead>
	<tbody>
		<%
			Iterator<ArrayList<String>> itr = lista.iterator();
			
			
			int rowid = 0;
			int i = 0;
			while (itr.hasNext()) 
			{
				i++;
				rowid = (rowid != 1 ? 1 : 2);
				ArrayList<String> record = (ArrayList<String>) itr.next();
		
		%>
		<tr class="row<%=rowid%>">
<%
			for(int j=0;j<15;j++)
			{
%>
				<td width="15%" nowrap><%=((record.get(j)==null || record.get(j).equals("-1") || record.get(j).equals("null"))?(""):(record.get(j))) %></td>
<%
			}
%>
			
		</tr>
		<%
			}
			%>
	</tbody>
</table>