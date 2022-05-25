<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.allerte.base.Ticket,com.zeroio.iteam.base.*" %>

<%@page import="org.aspcfs.modules.allerte.base.AslCoinvolte"%><jsp:useBean id="TicList" class="org.aspcfs.modules.allerte.base.TicketList" scope="request"/>
<jsp:useBean id="TicListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="TipoAlimento" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Origine" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentoInteressato" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AltreIrregolarita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="troubletickets_searchresults_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTicketsAllerte.do"><dhv:label name="sanzioniaa">Allerte</dhv:label></a> > 
<a href="TroubleTicketsAllerte.do?command=SearchTicketsForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
<dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>

<%
	int size = ( (User.getSiteId() > 0) ? (1) : (SiteIdList.size()) );
	int row_span = ( (User.getSiteId() > 0) ? (1) : (2) );
%>

<%-- End Trails --%>
<dhv:pagedListStatus title="Risultati Ricerca" object="TicListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
		<th width="8">
      &nbsp;
    </th>
    
    <th >
      <strong><dhv:label name="">Codice Allerta</dhv:label></strong>
    </th>
    
    <th >
      <strong><dhv:label name="">Azione non conforme Per </dhv:label></strong>
    </th>
    <th><b><dhv:label name="sanzionia.data_richiesta">Data Apertura</dhv:label></b></th>
    <th >
      <strong><dhv:label name="">Descrizione Breve</dhv:label></strong>
    </th>

    
  </tr>
<%
	Iterator j = TicList.iterator();
	if ( j.hasNext() ) {
		int i = 0;
    int rowid = 0;
		while (j.hasNext()) {
      i++;
      rowid = (rowid != 1?1:2);
      Ticket thisTic = (Ticket)j.next();
%>   
	<tr>
    <td rowspan="<%=row_span %>" width="2%" valign="top" nowrap class="row<%= rowid %>">
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= i %>','menuTicket', '<%= thisTic.getId() %>','<%= thisTic.isTrashed() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTicket');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
		
		
		<td rowspan="<%=row_span %>" valign="top" nowrap class="row<%= rowid %>">
  			<a href="TroubleTicketsAllerte.do?command=Details&id=<%= thisTic.getId() %>&return=searchResults"><%= thisTic.getIdAllerta() %></a>
		</td>
		
	<td rowspan="<%=row_span %>" valign="top" nowrap class="row<%= rowid %>">
  			 <%=(thisTic.getTipoCampione()==-1)?(" - "):("<b>- Tipo di Esame:</b> "+TipoCampione.getSelectedValue(thisTic.getTipoCampione()))%>
		</td>
		<td rowspan="<%=row_span %>" valign="top" class="row<%= rowid %>">
	      <zeroio:tz timestamp="<%= thisTic.getDataApertura() %>" dateOnly="true" timeZone="<%= thisTic.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
		</td>
		
		<td rowspan="<%=row_span %>" valign="top" nowrap class="row<%= rowid %>">
  			<%= toHtml(thisTic.getDescrizioneBreve()) %>
		</td>
		
		</tr>
				
			

	  <tr class="row<%= rowid %>">
    <td colspan="<%= 8 + size %>" valign="top">
<%
  if (1==1) {
    Iterator files = thisTic.getFiles().iterator();
    while (files.hasNext()) {
      FileItem thisFile = (FileItem)files.next();
      if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
%>
  <a href="TroubleTicketsDocumentsAllerte.do?command=Download&stream=true&tId=<%= thisTic.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
<%
      }
    }
  }
%>
      <%= (thisTic.getStato()) %>
     
    </td>
  </tr>

<%}%>
</table>
<br>
<input type="hidden" id="listFilter1" name="listFilter1" value='<%=request.getParameter("listFilter1")%>' />
<dhv:pagedListControl object="TicListInfo" tdClass="row1"/>
	<%} else {%>
		<tr class="containerBody">
      <td colspan="<%=7 + size %>">
        Nessuna Allerta Trovata
      </td>
    </tr>
  </table>
<%}%>

