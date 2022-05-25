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
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_details.jsp 19045 2007-02-07 18:06:22Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.acquedirete.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<%@page import="org.aspcfs.modules.audit.base.Audit"%>
<jsp:useBean id="TipoAcque" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="identificativoAcqua" class="java.lang.String" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.acquedirete.base.Organization" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="AcqueRete.do?command=SearchForm"><dhv:label name="">Acque Di Rete</dhv:label></a> > 
<dhv:label name="">Scheda Punto di prelievo Acque Di Rete</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

</dhv:evaluate>
<% String param1 = "orgId=" + OrgDetails.getOrgId();
   %>
  
<dhv:container name="acquedirete" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= OrgDetails.isTrashed() %>">
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>"> 


<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
 
    <dhv:permission name="acquedirete-acquedirete-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='AcqueRete.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
 
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="acquedirete-acquedirete-delete"><input type="button" value="<dhv:label name="">Elimina</dhv:label>" onClick="javascript:popURLReturn('AcqueRete.do?command=ConfirmDelete&id=<%=OrgDetails.getOrgId()%>&popup=true','AcqueRete.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
  	
  </dhv:evaluate>
</dhv:evaluate>

 <dhv:permission name="note_hd-view">
<jsp:include page="../note_hd/link_note_hd.jsp">
<jsp:param name="riferimentoId" value="<%=OrgDetails.getOrgId()%>" />
<jsp:param name="riferimentoIdNomeTab" value="organization" />
</jsp:include> <br><br>
</dhv:permission>


<jsp:include page="../preaccettazionesigla/button_preaccettazione.jsp">
    <jsp:param name="riferimentoIdPreaccettazione" value="<%=OrgDetails.getOrgId() %>" />
    <jsp:param name="riferimentoIdNomePreaccettazione" value="orgId" />
    <jsp:param name="riferimentoIdNomeTabPreaccettazione" value="organization" />
    <jsp:param name="userIdPreaccettazione" value="<%=User.getUserId() %>" />
</jsp:include>

  <%if (1==1) { %>

 <jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=OrgDetails.getOrgId() %>" />
     <jsp:param name="tipo_dettaglio" value="43" />
     </jsp:include>
   <% } else { %>  
    Dettaglio non gestito
<%} %>

<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
  
    <dhv:permission name="acquedirete-acquedirete-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='AcqueRete.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
 
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="acquedirete-acquedirete-delete"><input type="button" value="<dhv:label name="">Elimina</dhv:label>" onClick="javascript:popURLReturn('AcqueRete.do?command=ConfirmDelete&id=<%=OrgDetails.getOrgId()%>&popup=true','AcqueRete.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>
</dhv:evaluate>
</dhv:container>
<input type="hidden" name="source" value="searchForm">
<%= addHiddenParams(request, "popup|popupType|actionId") %>
<% if (request.getParameter("return") != null) { %>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>