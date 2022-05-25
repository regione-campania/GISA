<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
					<tr>						
						<td><%=c.getId_cane() %></td>
						<td><%=c.getMicrochip() %></td>
						<td><%=c.getProprietario() %></td>
						
						<% if(c.isPagato()==true) { %>
						<td>
  						<input type="radio" name="pagCane<%=i%>" value="true" disabled> Si 
						<input type="radio" name="pagCane<%=i%>" value="false" checked disabled> No<br>
						<input type="hidden" name="pagamentoCane<%=i%>" value="false">  
						</td>
						<td>						
						Questo animale risulta già pagato precedentemente						
						</td>	
						<%}
						else {
						%>
						
						<td>
  						<input type="radio" name="pagamentoCane<%=i%>" value="true" checked> Si
						<input type="radio" name="pagamentoCane<%=i%>" value="false" > No<br>
						</td>
						<td>
						</td>		
						<%}%>
					</tr>	
					
				<%}%>