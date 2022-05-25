<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="TicList" class="org.aspcfs.modules.campioni.base.TicketList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<% String orgId = (String)request.getParameter("orgId"); %>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="OsaSearch.do"><dhv:label name=" ">Operatori</dhv:label></a> > 
<a href="OsaSearch.do?command=Search"><dhv:label name="">Ricerca Operatori</dhv:label></a> >
<a href="PrintModulesHTML.do?command=Prenota&orgId=<%= orgId %>"><dhv:label name="">Prenotazione Campione</dhv:label></a> >
<dhv:label name="">Elenco Campioni Prenotati</dhv:label>
</td>
</tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
			<th colspan="5" style="background-color: rgb(204, 255, 153);" ><strong>
				<dhv:label name="">Elenco dei campioni prenotati</dhv:label>
		    </strong></th>
	    </tr>
	   
		<th>
      Tipo Attività
    </th>
    <th valign="center" align="left">
      <strong><dhv:label name="">Codice Attività</dhv:label></strong>
    </th>
     <th><b>Data Esecuzione Attività</b></th>
	<%
	
    Iterator j = TicList.iterator();
	int punteggioAccumulato=0;
    if ( j.hasNext() ) {
      int rowid = 0;
      int i =0;
     //if(thisTic.getIdControlloUfficiale().equals(TicketDetails.getPaddedId())){
      while (j.hasNext()) {      
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.campioni.base.Ticket thisTic = (org.aspcfs.modules.campioni.base.Ticket)j.next();
  %>
  
  <tr class="row<%= rowid %>">
	<td>
      <label>
      <b>Campione Num Verbale : <%= (thisTic.getLocation() != null && !thisTic.getLocation().equals("")) ? thisTic.getLocation() : "N.D." %>
      </b>
      </label>
	</td>
	<td  valign="top" nowrap>
		<a href="<%=thisTic.getURlDettaglio()%>Campioni.do?command=TicketDetails&id=<%= thisTic.getId() %>&orgId=<%= thisTic.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTic.getIdentificativo() %></a>
	</td>

	<td valign="top"> 
		<%= (thisTic.getAssignedDate() != null && !thisTic.getAssignedDate().equals("")) ? thisTic.getAssignedDate() : "N.D" %>
	</td>
	
  </tr>
  <%}%>
  <%} else {%>
  <tr class="containerBody">
    <td colspan="4">
      <dhv:label name="">Nessun Campione prenotato trovato</dhv:label><br />
    </td>
  </tr>
<%}%>
</table>