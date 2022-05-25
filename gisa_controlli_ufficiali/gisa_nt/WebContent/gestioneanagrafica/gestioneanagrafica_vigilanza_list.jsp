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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.base.*,org.aspcfs.modules.vigilanza.base.Ticket,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TicList" class="org.aspcfs.modules.vigilanza.base.TicketList" scope="request"/>
<jsp:useBean id="AccountTicketInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TipoIspezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%-- <%@ include file="accounts_vigilanza_list_menu.jsp" %> --%>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="OpuStab.do?command=SearchForm"><dhv:label name="">ANAGRAFICA STABILIMENTI</dhv:label></a> > 
  <a href="GestioneAnagraficaAction.do?command=Details&altId=<%=OrgDetails.getAltId() %>">Scheda</a> >
  <dhv:label name="vigilanza">Tickets</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>


  <%
String nomeContainer = "gestioneanagrafica";
String param = "altId="+OrgDetails.getAltId();

%>

  <dhv:container name="<%=nomeContainer %>" selected="Scheda" object="OrgDetails" param="<%=param%>">
    <dhv:permission name="gestioneanagrafica-gestioneanagrafica-vigilanza-add"><a href="GestioneAnagraficaVigilanza.do?command=Add&altId=<%= OrgDetails.getAltId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Aggiungi Nuovo Controllo Ufficiale</dhv:label></a></dhv:permission>
    <input type=hidden name="altId" value="<%= OrgDetails.getAltId() %>">
    <br>
  <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="AccountTicketInfo"/>





<%@page import="java.util.Iterator"%>


<%@page import="org.aspcfs.modules.vigilanza.base.Ticket"%>



<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    
        <tr>
        
	   <th><strong><dhv:label name="">Identificativo</dhv:label></strong></th>
      <th ><strong><dhv:label name="">Controllo del</dhv:label></strong></th>
      <th><b><dhv:label name="sanzionia.data_richiesta">Data Fine Controllo</dhv:label></b></th>
      <th><b><dhv:label name="sanzionia.richiedente">Tipo di controllo</dhv:label></b></th>
          <th><b><dhv:label name="sanzionia.richiedente">Linee Sottoposte A Controllo</dhv:label></b></th>
    
      <th><b><dhv:label name="sanzionia.richiedente">Punteggio</dhv:label></b></th>
      <th><b><dhv:label name="sanzionia.richiedente">Inserito da</dhv:label></b></th>
	  <th><b><dhv:label name="sanzionia.richiedente">Modificato da</dhv:label></b></th>
  </tr>
  <%
  int orgIdOperatore = OrgDetails.getAltId();
    Iterator j = TicList.iterator();
    if ( j.hasNext() ) {
      int rowid = 0;
      int i =0;
      while (j.hasNext()) {
        i++;
        rowid = (rowid != 1?1:2);
        Ticket thisTic = (Ticket)j.next();
        
        
  %>
    <tr class="row<%= rowid %>">
    
   <td width="10%" valign="top" nowrap>
		<%=thisTic.getPaddedId() %>
		</td>
		<td width="10%" valign="top" nowrap>
			<a href="GestioneAnagraficaVigilanza.do?command=TicketDetails&TimeIni=<%=System.currentTimeMillis() %>&container=gestioneanagrafica&id=<%= thisTic.getId() %>&altId=<%= thisTic.getAltId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><zeroio:tz timestamp="<%= thisTic.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisTic.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
     		</a>
		</td>
    	<td width="15%" valign="top" class="row<%= rowid %>">
      <zeroio:tz timestamp="<%= thisTic.getDataFineControllo() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
    	
		<%if(thisTic.getTipoCampione() > -1) {%>
		<td valign="top"><%= TipoCampione.getSelectedValue(thisTic.getTipoCampione()) %>
		
		</td>
		<%}else{%>
		<td>-
		</td>
		<%} %>
		
		<td valign="top">
    	<%
    	if(thisTic.getListaLineeProduttive().size()>0)
    		{
    		for(String linea : thisTic.getListaLineeProduttive() )
    			out.print(linea+"<br>");
    		}
    		
    		%>
		
		</td>
		
		
		<%if(thisTic.getPunteggio() >= 3) {%>
		<td valign="top"><%= thisTic.getPunteggio() %></td>	
		<%}else{%>
		<td><%=thisTic.getPunteggio()%> 
		</td>
		<%} %>

		<td nowrap>
		<%
		if (thisTic.getEnteredBy() == -3)
		{
			out.print("Utente Black Berry");
		}
		else
		{
		%>
      <dhv:username id="<%= thisTic.getEnteredBy() %>" />
      <%} %>
	</td>
	<td nowrap>
		<%
		if (thisTic.getEnteredBy() == -3)
		{
			out.print("Utente Black Berry");
		}
		else
		{
		%>
      <dhv:username id="<%= thisTic.getModifiedBy() %>" />
      <%} %>
	</td>
	</tr>
      
<tr class="row<%= rowid %>">
      <td colspan="9" valign="top">
        <%
          if (1==1) {
            Iterator files = thisTic.getFiles().iterator();
        
          }
        %>
        <%= toHtml(thisTic.getProblemHeader()) %>&nbsp;
        
        
<%String stato ="";
if (thisTic.getStatusId()==thisTic.STATO_APERTO && thisTic.isChiusura_attesa_esito() == true) 
  	stato = "<font color=\"red\">CONTROLLO CHIUSO IN ATTESA DI ESITO</font>";
else if (thisTic.getStatusId()==thisTic.STATO_CHIUSO)
	stato="<font color=\"red\">Chiuso</font>";
else if (thisTic.getStatusId()==thisTic.STATO_RIAPERTO)
	stato="<font color=\"orange\">Riaperto</font>";
else if (thisTic.getStatusId()==thisTic.STATO_ANNULLATO)
	stato="<font color=\"red\"><strike>Disattivato</strike></font>";
else if (thisTic.getStatusId()==thisTic.STATO_APERTO)
	stato="<font color=\"green\">Aperto</font>";
%>
        
      [<%=stato %>]  
        
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