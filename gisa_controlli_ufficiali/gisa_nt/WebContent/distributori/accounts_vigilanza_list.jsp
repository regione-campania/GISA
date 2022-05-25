<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_tickets_list.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.distributori.base.*,org.aspcfs.modules.vigilanza.base.Ticket,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.distributori.base.Organization" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoIspezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoAudit" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TicList" class="org.aspcfs.modules.vigilanza.base.TicketList" scope="request"/>
<jsp:useBean id="AccountTicketInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="NewDistributore" class="org.aspcfs.modules.distributori.base.Distrubutore" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_vigilanza_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  
</script>
<%int idMacchinetta=Integer.parseInt(""+request.getAttribute("id"));%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Distributori.do?command=List"><dhv:label name="">Distributori Automatici</dhv:label></a> > 



<a href="Distributori.do?command=Details&orgId=<%=OrgDetails.getOrgId() %>&id=<%=idMacchinetta %>"><dhv:label name="">Scheda Distributore Automatico</dhv:label></a> >

<dhv:label name="vigilanza">Tickets</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>

<% String param1 = "orgId=" + OrgDetails.getOrgId()+"&id="+request.getAttribute("id")+"&asl="+NewDistributore.getAslMacchinetta();


   %>
  
<dhv:container name="distributoridettaglio" selected="vigilanza" object="OrgDetails" param="<%= param1 %>"  appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>">

  
  <%UserBean user=(UserBean)session.getAttribute("User");
	//String idaslMacchinetta=(String)request.getAttribute("");
  
	
  
  String aslMacchinetta=(String)request.getAttribute("aslMacchinetta");
  if(aslMacchinetta!=null)
  {
	  if(aslMacchinetta.equals("-1"))
	  {
		  aslMacchinetta="16";
	  }
  }
  
  if(user.getSiteId()!=-1){
	  
  if((""+user.getSiteId()).equals(aslMacchinetta)){
  
  %>
  <dhv:evaluate if="<%= !OrgDetails.isTrashed() %>">
    <dhv:permission name="distributori-distributori-vigilanza-add"><a href="DistributoriVigilanza.do?command=Add&aslMacchinetta=<%=aslMacchinetta %>&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|popupType|actionId")%>&id=<%=idMacchinetta %>"><dhv:label name="accounts.richiesta.add">Aggiungi Nuovo Controllo Ufficiale</dhv:label></a></dhv:permission>
  </dhv:evaluate>
    <input type=hidden name="orgId" value="<%= OrgDetails.getOrgId() %>">
    <input type=hidden name="id" value="<%= idMacchinetta %>">
  
  <%}} else{%>
  
    <dhv:evaluate if="<%= !OrgDetails.isTrashed() %>">
    <dhv:permission name="distributori-distributori-vigilanza-add"><a href="DistributoriVigilanza.do?command=Add&aslMacchinetta=<%=aslMacchinetta %>&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|popupType|actionId")%>&id=<%=idMacchinetta %>"><dhv:label name="accounts.richiesta.add">Aggiungi Nuovo Controllo Ufficiale</dhv:label></a></dhv:permission>
  </dhv:evaluate>
    <input type=hidden name="orgId" value="<%= OrgDetails.getOrgId() %>">
    <input type=hidden name="id" value="<%= idMacchinetta %>">
  
  <%} %>
	
  

  
    <br>
  <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="AccountTicketInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
   
        <tr>
		
     <th valign="center" align="left">
      <strong><dhv:label name="quotes.numbeddr">Controllo del</dhv:label></strong>
    </th>
     <th><b><dhv:label name="sanzionia.dataaa_richiesta">Data fine controllo</dhv:label></b></th>
    <th><b><dhv:label name="sanzionia.richiedente">Tipo di controllo</dhv:label></b></th>
     <th><b><dhv:label name="sanzionia.richiedente">Modificato</dhv:label></b></th>
 </tr>
  <%
    Iterator j = TicList.iterator();
    boolean hasNext = j.hasNext();
    if ( hasNext ) {
      int rowid = 0;
      int i =0;
     
      while ( hasNext ) {
        i++;
        rowid = (rowid != 1?1:2);
    	  
        Ticket thisTic = (Ticket)j.next();
    	  
        hasNext = j.hasNext();
  %>
    <tr class="row<%= rowid %>">
     
      
		<td width="10%" valign="top" nowrap>
			<a href="DistributoriVigilanza.do?command=TicketDetails&id=<%= thisTic.getId() %>&orgId=<%= thisTic.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>&idmacchinetta=<%=idMacchinetta %>"><zeroio:tz timestamp="<%= thisTic.getDataInizioControllo() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
     </a>
		</td>
     
		<td width="15%" valign="top" class="row<%= rowid %>">
      <%-- if(!User.getTimeZone().equals(thisTic.getAssignedDate())){--%>
      <zeroio:tz timestamp="<%= thisTic.getDataFineControllo() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
      <%-- } else { %>
      <zeroio:tz timestamp="<%= thisTic.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisTic.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
      <% } --%>
		</td>
			<%if(thisTic.getTipoCampione() > -1) {%>
		<td valign="top"><%= TipoCampione.getSelectedValue(thisTic.getTipoCampione()) %>
		<%if(thisTic.getTipoCampione()==3){%>
		<%= " - " + AuditTipo.getSelectedValue(thisTic.getAuditTipo()) %>
		
		
		<%}%>
		</td>
		<%}else{%>
		<td>-
		</td>
		<%} %>
		<td width="45%" valign="top">
			<zeroio:tz timestamp="<%= thisTic.getModified() %>" dateOnly="false" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
		</td>
	</tr>
   
<tr class="row<%= rowid %>">
      <td colspan="7" valign="top">
        <%
          if (1==1) {
            Iterator files = thisTic.getFiles().iterator();
            while (files.hasNext()) {
              FileItem thisFile = (FileItem)files.next();
              if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
        %>
          <a href="DistributoriVigilanzaDocuments.do?command=Download&stream=true&tId=<%= thisTic.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
        <%
              }
            }
          }
        %>
        <% if (thisTic.getClosed() == null) { %>
          [<font color="green"><dhv:label name="projecta.open.lowercase1">pratica aperta</dhv:label></font>]
        <%} else {%>
          [<font color="red"><dhv:label name="projecta.closed.lowercase1">pratica chiusa</dhv:label></font>]
        <%}%> &nbsp;<%= toHtml(thisTic.getProblemHeader()) %>
      </td>
    </tr>
  <%}%>
  <%} else {%>
  
    <tr class="containerBody">
      <td colspan="7">
        <dhv:label name="accounts.richieste.search.notFound">Nessun Controllo Ufficiale Trovato.</dhv:label>
      </td>
    </tr>
  <%}%>
  </table>
	<br>
  <dhv:pagedListControl object="AccountTicketInfo"/>
</dhv:container>