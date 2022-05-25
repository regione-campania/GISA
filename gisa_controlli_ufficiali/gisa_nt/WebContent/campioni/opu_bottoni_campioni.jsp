<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<input type="button" value="<dhv:label name="button.insert">Insert</dhv:label>" id="Save" name="Save" class="Save" onClick="javascript:controllaAnaliti();" />

<% if( OrgDetails.getTipologia() != 1000 ) { %>

<dhv:permission name="campioni-campioni-addconpreaccettazione-view"> 
<input type="button" value="<dhv:label name="">inserisci con preaccettazione</dhv:label>" id="SaveConPreacc" name="SaveConPreacc" class="Save" onClick="javascript:controllaAnalitiConPreacc();" />
 </dhv:permission> 

<% } %>

<% if (OrgDetails.getTipologia() == 2000) { %>
	<input type="button" value="Annulla" onClick="window.location.href='<%=OrgDetails.getAction() %>Vigilanza.do?command=TicketDetails&id=<%=request.getAttribute("idC") %>&altId=<%=OrgDetails.getAltId() %>';this.form.dosubmit.value='false';" />
<% } else { %>
	<input type="button" value="Annulla" onClick="window.location.href='<%=OrgDetails.getAction() %>Vigilanza.do?command=TicketDetails&id=<%=request.getAttribute("idC") %>&idStabilimentoopu=<%=OrgDetails.getIdStabilimento() %>';this.form.dosubmit.value='false';" />
<% } %>
