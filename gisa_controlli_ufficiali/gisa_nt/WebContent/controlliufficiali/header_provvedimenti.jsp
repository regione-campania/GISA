<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%if (TicketDetails.isflagBloccoCu()==true){
		%>
		<font color = "red" ><%=TicketDetails.getNoteBlocco() %></font><br><br>
	
	<% if (TicketDetails.getClosed() == null) { %>
		
		<% if(TicketDetails.getIdStabilimento() > 0 || TicketDetails.getOrgId() > 0) { %>
		
		<dhv:permission name="operazioni-hd-view">
		</dhv:permission>
		<% } else { %>
		<dhv:permission name="operazioni-hd-view">
		</dhv:permission>
		<% } %>
	<% }
			}

		if (TicketDetails.isTrashed() && !TicketDetails.isflagBloccoCu()) {
	%>
	<dhv:permission name="<%=permission_op_edit%>" >
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio() %>AltreNonConformita.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		}else if (!TicketDetails.isflagBloccoCu() && (TicketDetails.getClosed() != null || TicketDetails.isChiusura_attesa_esito()==true)) {
			if (TicketDetails.getClosed() != null && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo())
			{
	%>
	<%if(TicketDetails.getIdStabilimento() > 0 || TicketDetails.getOrgId() > 0) {%>
	<%if (CU.getStatusId()!=2){ %>
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio() %>AltreNonConformita.do?command=ReopenTicket&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';loadModalWindow();submit();">
	</dhv:permission>
	<%} } else { %>
	<%if (CU.getStatusId()!=2){ %>
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='ApicolturaApiariAltreNonConformita.do?command=ReopenTicket&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';loadModalWindow();submit();">
	</dhv:permission>
	
	<% } } %>
	<%
			}
	
		} else {
			if (TicketDetails.isflagBloccoCu()==false && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo())
			{
	%>
	<%if(TicketDetails.getIdStabilimento() > 0 || TicketDetails.getOrgId() > 0) {%>
	<dhv:permission name="<%=permission_op_edit%>">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio() %>AltreNonConformita.do?command=ModifyTicket&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';loadModalWindow();submit();">
	</dhv:permission>
<%} else { %>
<dhv:permission name="<%=permission_op_edit%>">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='ApicolturaApiariAltreNonConformita.do?command=ModifyTicket&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';loadModalWindow();submit();">
	</dhv:permission>

<% } %>
	
	<dhv:permission name="<%=permission_op_del%>">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<% if(TicketDetails.getIdStabilimento() > 0 || TicketDetails.getOrgId() > 0) { %>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('<%=TicketDetails.getURlDettaglio() %>AltreNonConformita.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&stabId=<%=TicketDetails.getIdStabilimento()%>&orgId=<%=TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<% } else {%>
			<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('ApicolturaApiariAltreNonConformita.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&stabId=<%=TicketDetails.getIdApiario()%>&orgId=<%=TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<% }
		
			} else {
		%>
		<% if(TicketDetails.getIdStabilimento() > 0 || TicketDetails.getOrgId() > 0)  {%>
		 <input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('<%=TicketDetails.getURlDettaglio() %>AltreNonConformita.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&stabId=<%=TicketDetails.getIdStabilimento()%>&orgId=<%=TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<% } else { %>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('ApicolturaApiariAltreNonConformita.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&stabId=<%=TicketDetails.getIdApiario()%>&orgId=<%=TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<% } %>	 
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="<%=permission_op_edit%>">
	<% if(TicketDetails.getIdStabilimento() > 0 || TicketDetails.getOrgId() > 0) {%>
		<% } else { %>
	<% } %>
	</dhv:permission>
		<%} %>

	<%
		
		}
	%>