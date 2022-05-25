<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Iterator"%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
			<th colspan="5" style="background-color: rgb(204, 255, 153);" ><strong>
				<dhv:label name="">Attività Svolte Durante il Controllo Ufficiale</dhv:label>
		    </strong></th>
	    </tr>
	  
	   <tr>
		<th>
      Tipo Attività
    </th>
    <th valign="center" align="left">
      <strong><dhv:label name="">Codice Attività</dhv:label></strong>
    </th>
     <th><b><dhv:label name="sanzionia.data_richiesta">Data Esecuzione Attività</dhv:label></b></th>
    
    <th><b><dhv:label name="sanzionia.richiedente">Punteggio</dhv:label></b></th>
    <th>Stato</th>
  </tr>
	<%
	
    Iterator j = TicList.iterator();
	
    if ( j.hasNext() ) {
      int rowid = 0;
      int i =0;
     //if(thisTic.getIdControlloUfficiale().equals(TicketDetails.getPaddedId())){
      while (j.hasNext()) {      
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.campioni.base.Ticket thisTic = (org.aspcfs.modules.campioni.base.Ticket)j.next();
       //if(TicketDetails.getPaddedId()==thisTic.getIdControlloUfficiale()){
        punteggioAccumulato += thisTic.getPunteggio();
  %>
  
  <tr class="row<%= rowid %>">
      <td  width="10" valign="top" nowrap>
      
     
         <%
         if(thisTic.getTipologia()==2){ %>
        <label><b>Campione Num Verbale : <%=thisTic.getLocation() != null ? thisTic.getLocation() : "N.D." %></b></label>
        <%} %>
       </td>
		<%--Gestione stabId --%>
		<% if(thisTic.getOrgId() >0 || thisTic.getIdStabilimento() > 0) {%>      
		<td  valign="top" nowrap>
				<a href="<%=TicketDetails.getURlDettaglio() %>Campioni.do?command=TicketDetails&container=archiviati&stabId=<%=OrgDetails.getIdStabilimento()%>&id=<%= thisTic.getId() %>&orgId=<%= thisTic.getOrgId()%>&altId=<%=thisTic.getAltId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTic.getIdentificativo() %></a>
		</td> 
		<% }else { %>
		<td  valign="top" nowrap>
				<a href="<%=TicketDetails.getURlDettaglio() %>Campioni.do?command=TicketDetails&container=archiviati&id=<%= thisTic.getId() %>&stabId=<%= thisTic.getIdApiario()%>&altId=<%=thisTic.getAltId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTic.getIdentificativo() %></a>
		</td> 
		<%} %>
     
		<td valign="top" class="row<%= rowid %>">
      <% if(!User.getTimeZone().equals(thisTic.getAssignedDate())){%>
      <zeroio:tz timestamp="<%= thisTic.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisTic.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisTic.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
      <% } %>
		</td>
		<%if(thisTic.getPunteggio() > -1) {%>
		<td width="10%" valign="middle"><%= thisTic.getPunteggio() %></td>	
		<%}else{%>
		<td>-
		</td>
		<%} %>
		<td>
		<% if (thisTic.getClosed() == null) { %>
          [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
        <%} else {%>
          [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
        <%}%>
      </td>
		
	</tr>
	
    
  <%}%>
  <%} %>
  <%
	
    Iterator a = TamponiList.iterator();
	
    if ( a.hasNext() ) {
      int rowid = 0;
      int i =0;
     
      while (a.hasNext()) {      
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.tamponi.base.Ticket thisTam = (org.aspcfs.modules.tamponi.base.Ticket)a.next();
      
        punteggioAccumulato += thisTam.getPunteggio();
  %>
  <tr class="row<%= rowid %>">
      <td  width="10" valign="top" nowrap>
        
        <%if(thisTam.getTipologia()==7){ %>
        <label><b>Tampone Num Verbale : <%=thisTam.getLocation() != null ? thisTam.getLocation() : "N.D." %></b></label>
        <%} %>
        </td>
      <%if(thisTam.getOrgId() > 0 || thisTam.getIdStabilimento() > 0) {%>
		<td  valign="top" nowrap>
				<a href="<%=TicketDetails.getURlDettaglio() %>Tamponi.do?command=TicketDetails&container=archiviati&id=<%= thisTam.getId() %>&orgId=<%= thisTam.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTam.getIdentificativo() %></a>
		</td>
		<%} else { %>
		<td  valign="top" nowrap>
				<a href="<%=TicketDetails.getURlDettaglio() %>Tamponi.do?command=TicketDetails&container=archiviati&id=<%= thisTam.getId() %>&stabId=<%= thisTam.getIdApiario()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTam.getIdentificativo() %></a>
		</td>
		<%} %>
  
		<td valign="top" class="row<%= rowid %>">
      <% if(!User.getTimeZone().equals(thisTam.getAssignedDate())){%>
      <zeroio:tz timestamp="<%= thisTam.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisTam.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisTam.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
      <% } %>
		</td>
		<%if(thisTam.getPunteggio() > -1) {%>
		<td width="10%" valign="middle"><%= thisTam.getPunteggio() %></td>	
		<%}else{%>
		<td>-
		</td>
		<%} %>
		<td> <% if (thisTam.getClosed() == null) { %>
          [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
        <%} else {%>
          [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
        <%}%>
      </td>
	</tr>
	
    
  <%}%>
  <%} %>
    
   <%
	
    Iterator aa = ProvvedimentiList.iterator();
	
    if ( aa.hasNext() ) {
      int rowid = 0;
      int i =0;
     
      while (aa.hasNext()) {      
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.prvvedimentinc.base.Ticket thisTam = (org.aspcfs.modules.prvvedimentinc.base.Ticket)aa.next();
      
       
  %>
  <tr class="row<%= rowid %>">
      <td  width="10" valign="top" nowrap>
        
        
        <label><b>Provvedimento</b></label>
    
        </td>
		<%if(thisTam.getOrgId()>0 || thisTam.getIdStabilimento() > 0){ %>	      
		<td  valign="top" nowrap>
				<a href="<%=TicketDetails.getURlDettaglio() %>Provvedimenti.do?command=TicketDetails&container=archiviati&id=<%= thisTam.getId() %>&orgId=<%= thisTam.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTam.getIdentificativo() %></a>
		</td>
		<% } else { %>
		<td  valign="top" nowrap>
				<a href="<%=TicketDetails.getURlDettaglio() %>Provvedimenti.do?command=TicketDetails&container=archiviati&id=<%= thisTam.getId() %>&stabId=<%= thisTam.getIdApiario()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTam.getIdentificativo() %></a>
		</td>
  		<% } %>
		<td valign="top" class="row<%= rowid %>">
     	Non Previsto
		</td>
		
		<td width="10%" valign="middle">Non Previsto</td>	
		
		<td>  <% if (thisTam.getClosed() == null) { %>
          [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
        <%} else {%>
          [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
        <%}%>
      </td>
      
	</tr>
	
    
  <%}}%>
  
  
   
  
  <%
	
    Iterator z = NonCList.iterator();
	
    if ( z.hasNext() ) {
      int rowid = 0;
      int i =0;
     //if(thisTic.getIdControlloUfficiale().equals(TicketDetails.getPaddedId())){
      while (z.hasNext()) {      
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.nonconformita.base.Ticket thisNonC = (org.aspcfs.modules.nonconformita.base.Ticket)z.next();
       //if(TicketDetails.getPaddedId()==thisTic.getIdControlloUfficiale()){
 
  %>
  <tr class="row<%= rowid %>">
      <td  width="10" valign="top" nowrap>
        <%if(thisNonC.getTipologia()==8){ %>

        <label><b>Non Conformità Rilevate</b></label>

        <%} %>
       </td>
      <%if(thisNonC.getOrgId() > 0 || thisNonC.getIdStabilimento() > 0) {%>
		<td  valign="top" nowrap>
			<a href="<%=TicketDetails.getURlDettaglio() %>NonConformita.do?command=TicketDetails&container=archiviati&idIspezione=<%= TicketDetails.getTipoIspezione() %>&id=<%= thisNonC.getId() %>&orgId=<%= thisNonC.getOrgId()%>&altId=<%=TicketDetails.getAltId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisNonC.getIdentificativo() %></a>
		</td>
      <%} else { %>
      <td  valign="top" nowrap>
			<a href="<%=TicketDetails.getURlDettaglio() %>NonConformita.do?command=TicketDetails&container=archiviati&idIspezione=<%= TicketDetails.getTipoIspezione() %>&id=<%= thisNonC.getId() %>&stabId=<%= thisNonC.getIdApiario()%>&altId=<%=TicketDetails.getAltId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisNonC.getIdentificativo() %></a>
		</td>
      <%} %>
		<td valign="top" class="row<%= rowid %>">
      <% if(!User.getTimeZone().equals(thisNonC.getAssignedDate())){%>
      <zeroio:tz timestamp="<%= thisNonC.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisNonC.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisNonC.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
      <% } %>
		</td>
		<%if(thisNonC.getPunteggio() > -1 && TicketDetails.getTipoCampione()!=5) {%>
		<td width="10%" valign="middle"><%= thisNonC.getPunteggio() %></td>	
		<%}else{%>
		<td>Non Previsto
		</td>
		<%} %>
		<td>
		<% if (thisNonC.getClosed() == null) { %>
          [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
        <%} else {%>
          [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
        <%}%>
      </td>
	</tr>
	
    
  <%}%>
  <%} %>
  <%--/table--%>
  
  
  
  
  
  
  <%
	
     z = AltreNonCList.iterator();
	
    if ( z.hasNext() ) {
      int rowid = 0;
      int i =0;
     //if(thisTic.getIdControlloUfficiale().equals(TicketDetails.getPaddedId())){
      while (z.hasNext()) {      
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.altriprovvedimenti.base.Ticket thisNonC = (org.aspcfs.modules.altriprovvedimenti.base.Ticket)z.next();
       //if(TicketDetails.getPaddedId()==thisTic.getIdControlloUfficiale()){
  
  %>
   <tr class="row<%= rowid %>">
      <td  width="10" valign="top" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <%-- To display the menu, pass the actionId, accountId and the contactId--%>
        <%if(thisNonC.getTipologia()==10){ %>

        <label><b>Altre Non Conformità Rilevate non a carico</b></label>

        <%} %>
       </td>
      <%if(thisNonC.getOrgId() >0 || thisNonC.getIdStabilimento()>0) {%>
		<td  valign="top" nowrap>
			<a href="<%=TicketDetails.getURlDettaglio() %>AltreNonConformita.do?command=TicketDetails&container=archiviati&idIspezione=<%= TicketDetails.getTipoIspezione() %>&id=<%= thisNonC.getId() %>&orgId=<%= thisNonC.getOrgId()%>&altId=<%=TicketDetails.getAltId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisNonC.getIdentificativo() %></a>
		</td>
		<%} else { %>
		<td  valign="top" nowrap>
			<a href="<%=TicketDetails.getURlDettaglio() %>AltreNonConformita.do?command=TicketDetails&container=archiviati&idIspezione=<%= TicketDetails.getTipoIspezione() %>&id=<%= thisNonC.getId() %>&stabId=<%= thisNonC.getIdApiario()%>&altId=<%=TicketDetails.getAltId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisNonC.getIdentificativo() %></a>
		</td>
		<%} %>
      
		<td valign="top" class="row<%= rowid %>">
      <% if(!User.getTimeZone().equals(thisNonC.getAssignedDate())){%>
      <zeroio:tz timestamp="<%= thisNonC.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisNonC.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisNonC.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
      <% } %>
		</td>
		<%if(thisNonC.getPunteggio() > -1 && TicketDetails.getTipoCampione()!=5) {%>
		<td width="10%" valign="middle"><%= thisNonC.getPunteggio() %></td>	
		<%}else{%>
		<td>Non Previsto
		</td>
		<%} %>
		<td>
		<% if (thisNonC.getClosed() == null) { %>
          [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
        <%} else {%>
          [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
        <%}%>
      </td>
	</tr>
	
    
  <%}%>
  <%} %>
  <%--/table--%>
  

  </table>