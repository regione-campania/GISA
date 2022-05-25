<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.allerte_new.base.*,org.aspcfs.modules.allerte_new.base.Ticket,com.zeroio.iteam.base.*,java.text.DateFormat" %>
<%--jsp:useBean id="CreatedByMeList" class="org.aspcfs.modules.allerte_new.base.TicketList" scope="request"/--%>
<jsp:useBean id="CreatedByMeInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%--jsp:useBean id="AssignedToMeList" class="org.aspcfs.modules.allerte_new.base.TicketList" scope="request"/--%>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AssignedToMeInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%--jsp:useBean id="OpenList" class="org.aspcfs.modules.allerte_new.base.TicketList" scope="request"/ --%>
<jsp:useBean id="OpenInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AllTicketsList" class="org.aspcfs.modules.allerte_new.base.TicketList" scope="request"/>
<jsp:useBean id="AllTicketsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%--jsp:useBean id="UserGroupTicketList" class="org.aspcfs.modules.allerte_new.base.TicketList" scope="request"/--%>
<jsp:useBean id="UserGroupTicketInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TipoAlimento" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Origine" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentoInteressato" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AltreIrregolarita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="troubletickets_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%-- Preload image rollovers for drop-down menu --%>
<script language="JavaScript" type="text/javascript">
  
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTicketsAllerteNew.do"><dhv:label name="campionis">Allerte</dhv:label></a> > 
<dhv:label name="Sanzioni.visualizzas">Visualizza Allerte</dhv:label> 
</td>
</tr>
</table>
<%-- End Trails --%>

<% 
	int count = 0;
	int size = ( (User.getSiteId() > 0) ? (1) : (SiteIdList.size()) );
	int row_span = 0 ;// ( (User.getSiteId() > 0) ? (1) : (2) );
%>

<dhv:include name="ticketList.allTickets" none="true">
<% if ( (request.getParameter("pagedListSectionId") == null && !(AssignedToMeInfo.getExpandedSelection()) && !(OpenInfo.getExpandedSelection()) && !(CreatedByMeInfo.getExpandedSelection()) && !(UserGroupTicketInfo.getExpandedSelection())) || AllTicketsInfo.getExpandedSelection()) { %>
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="true" title="Tutti le Allerte" type="richieste.tutte" object="AllTicketsInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		
  
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
	Iterator j = AllTicketsList.iterator();
	if ( j.hasNext() ) {
		int i = 0;
		int rowid = 0;
		while (j.hasNext()) {
			i++;
      		++count;
		    rowid = (rowid != 1?1:2); 
		    Ticket thisTic = (Ticket)j.next();
%>   

	<tr>
   
   
    
    
    
		
		<td rowspan="<%=row_span %>" valign="top" nowrap class="row<%= rowid %>">
  			<a href="TroubleTicketsAllerteNew.do?command=Details&id=<%= thisTic.getId() %>&return=searchResults"><%= thisTic.getIdAllerta() %></a>
		</td>
		<td rowspan="<%=row_span %>" valign="top" nowrap class="row<%= rowid %>">
  			 <%=(thisTic.getTipoCampione()==-1 && thisTic.getTipo_esame() != "")?("<b>- Tipo di Esame:</b> "+thisTic.getTipo_esame()):("<b>- Tipo di Esame:</b> "+TipoCampione.getSelectedValue(thisTic.getTipoCampione()))%>
		</td>
		<td rowspan="<%=row_span %>" valign="top" class="row<%= rowid %>">
	      <zeroio:tz timestamp="<%= thisTic.getDataApertura() %>" dateOnly="true" timeZone="<%= thisTic.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
		</td>
		
		<td rowspan="<%=row_span %>" valign="top" nowrap class="row<%= rowid %>">
  			<%= toHtml(thisTic.getDescrizioneBreve()) %>
		</td>
		
		
	
		
		
		
	</tr>

  <tr class="row<%= rowid %>">
    <td colspan="<%= 7 + size %>" valign="top">
<%
  if (1==1) {
    Iterator files = thisTic.getFiles().iterator();
    while (files.hasNext()) {
      FileItem thisFile = (FileItem)files.next();
      if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
%>
  <a href="TroubleTicketsDocumentsAllerteNew.do?command=Download&stream=true&tId=<%= thisTic.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
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
  <% if (AllTicketsInfo.getExpandedSelection()) {%>
<br>
<dhv:pagedListControl object="AllTicketsInfo" tdClass="row1"/>
  <%}%>
	<%} else {%>
		<tr class="containerBody">
      <td colspan="<%= 7 + size %>">
        <dhv:label name="sanzioniaa.nessun_risultato">Nessuna Allerta Trovata</dhv:label>
      </td>
    </tr>
  </table>
	<%}%>
<%}%>
</dhv:include>

