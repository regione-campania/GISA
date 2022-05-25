<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

	
		
			<dhv:evaluate if="<%= SiteIdList.size() > 1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.site">Site</dhv:label></td>
					<td><%=SiteIdList.getSelectedValue(TicketDetails
										.getSiteId())%>
					<%
						
					%> <input type="hidden"
						name="siteId" value="<%=TicketDetails.getSiteId()%>"></td>
				</tr>
			</dhv:evaluate>
			<dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
				<input type="hidden" name="siteId" id="siteId" value="-1" />
						</dhv:evaluate>
	
		
  <input type="hidden" name="id" id="id"
			value="<%=  TicketDetails.getId() %>" />
		<input type="hidden" name="stabId" id="orgId"
			value="<%=  TicketDetails.getIdStabilimento() %>" />

  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Codice FollowUp</dhv:label>
    </td>
   
     
      <td>
      		<%= toHtmlValue(TicketDetails.getIdentificativo()) %>
      </td>
    
  </tr>	
		
	<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="">Provvedimenti Adottati</dhv:label>
					</td>
					<td>
					<%
					HashMap<Integer,String> lista=TicketDetails.getListaLimitazioniFollowup();
					Iterator<Integer> it=lista.keySet().iterator();
					while(it.hasNext()){
						int k=it.next();
						out.print(lista.get(k)+",");
						
					}
					
					
					%>
					</td>
				</tr>
				
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="">Note</dhv:label>
					</td>
					<td>
					<%=toString(TicketDetails.getNote())%>
					</td>
				</tr>
		
		
