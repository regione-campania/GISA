<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr><th colspan="2"><strong>Gestione sanitaria</strong></th>
	
	<tr>
		<td nowrap class="formLabel" > Stato classificazione</td>
		<td><%=StatiClassificazione.getSelectedValue(OrgDetails.getStatoClassificazione()) %></td>
	</tr>


<% if (OrgDetails.getStatoClassificazione()==Organization.STATO_CLASSIFICAZIONE_REVOCATO){ %>
	<tr>
		<td nowrap class="formLabel" > Data Revoca</td>
		<td><%=toDateasString(OrgDetails.getDataRevoca()) %></td>
	</tr>
	<%} %>
	<% if (OrgDetails.getStatoClassificazione()==Organization.STATO_CLASSIFICAZIONE_SOSPESO){ %>
	<tr>
		<td nowrap class="formLabel" > Data Sospensione</td>
		<td><%=toDateasString(OrgDetails.getDataSospensione()) %></td>
	</tr>
	<%} %>


<%if(OrgDetails.getDataProvvedimento()!=null)
		{
	%>
	<tr id = "zc4">
		<td nowrap class="formLabel" > Data Provvedimento</td>
		<td>
		<%if(OrgDetails.getDataProvvedimento()!=null) {
			%>
			<%=sdf.format(new Date(OrgDetails.getDataProvvedimento().getTime())) %>
			<%
		}
		%>
			
		</td>
	</tr>
	<%} %>
	
	<%if(OrgDetails.getProvvedimento()!=null && !"".equals(OrgDetails.getProvvedimento()))
		{%>
	<tr id = "zc5">
		<td nowrap class="formLabel" name="orgname1" id="orgname1"> Provvedimento restrittivo in atto </td>
		<td><%=toHtml2(OrgDetails.getProvvedimento()) %></td>
	</tr>
	<%} %>


	
	<%
	if(OrgDetails.getProvvedimentiRestrittivi()==13)
	{
	%>
	<tr>
		<td nowrap class="formLabel" >Data Rifiuto</td>
		<td>
		<%=toDateasString(OrgDetails.getDataRifiuto()) %> </td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel" >Motivazione Rifiuto</td>
		<td>
		<%=RifiutoClassificazione.getSelectedValue(OrgDetails.getIdMotivazioneRifiuto()) %> </td>
	</tr>
	<%} %>
	
<!-- 	<tr> -->
<!-- 		<td nowrap class="formLabel" > Stato</td> -->
<!-- 		<td> -->
<%-- 		<%=OrgDetails.getStato() %> </td> --%>
<!-- 	</tr> -->
	
	
</table>