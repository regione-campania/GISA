<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<tr id="preavviso"   class="containerBody">
		<td  class="formLabel">
			Effettuato Preavviso
			</td>
		<td>
		<%if ("N".equalsIgnoreCase(TicketDetails.getFlag_preavviso()) ){%>Nessun Preavviso<%} %> 
		<%if ("P".equalsIgnoreCase(TicketDetails.getFlag_preavviso()) ){%>Telefono<%} %>
		<%if ("T".equalsIgnoreCase(TicketDetails.getFlag_preavviso()) ){%>Telegramma<%} %>
		<%if ("A".equalsIgnoreCase(TicketDetails.getFlag_preavviso()) ){%>Altro<%} %>
		</td>
		</tr>
		
		
		<%if (TicketDetails.getData_preavviso_ba() != null) {%>
		<tr id="data_preavviso_ba_tr" class="containerBody">
		<td  class="formLabel">
			Data Preavviso
			</td>
		<td>
		
		<%=toDateasString(TicketDetails.getData_preavviso_ba())%>
		
		</td>
		</tr>
		<%}%>