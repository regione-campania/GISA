<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<input type="hidden" name="org_id" id="org_id" value="<%=TicketDetails.getOrgId()%>" />

<%if (TicketDetails.isflagBloccoCu()==true){
		%>
		<font color = "red" ><%=TicketDetails.getNoteBlocco() %></font><br><br> 
		
		<% if (TicketDetails.getClosed() == null) { %>
		

			
		<% }
			}

		if (TicketDetails.isTrashed() && TicketDetails.isflagBloccoCu()==false) {
	%>
	<dhv:permission name="<%=permission_op_edit%>" >
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio() %>Sanzioni.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		}else if (TicketDetails.isflagBloccoCu()==false && (TicketDetails.getClosed() != null || TicketDetails.isChiusura_attesa_esito()==true)) {
			if (TicketDetails.getClosed() != null && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo())
			{
	%>

	<%
			}
	
		} else {
			if (TicketDetails.isflagBloccoCu()==false && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo())
			{
	%>
	
	<% if (CU.getStatusId()==1){ %>

	
	
	<dhv:permission name="<%=permission_op_del%>">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('<%=TicketDetails.getURlDettaglio() %>Sanzioni.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&stabId=<%=TicketDetails.getIdStabilimento()%>&orgId=<%=TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		 <input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('<%=TicketDetails.getURlDettaglio() %>Sanzioni.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&stabId=<%=TicketDetails.getIdStabilimento()%>&orgId=<%=TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');"> 
		<%
			}
		%>
	</dhv:permission>
	
	<%} %>
	
	

	<%
			}//fine blocco
		} 
	%>
	
<input type="hidden" id="nomePermesso" name="nomePermesso" value="<%=permission_op_del%>"/>
	
<input type="button" value="Torna a non conformità" onClick="loadModalWindow(); window.location.href='<%=TicketDetails.getURlDettaglio()+"NonConformita.do?command=TicketDetails&id="+TicketDetails.getId_nonconformita()+"&orgId="+TicketDetails.getOrgId()%>'"/>