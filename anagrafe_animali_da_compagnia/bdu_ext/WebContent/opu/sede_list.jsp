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
  - Version: $Id: accounts_list.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description:
  --%> 
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat,org.aspcfs.modules.accounts.base.*" %>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="sedeList" class="org.aspcfs.modules.opu.base.SedeList" scope="request"/>
<jsp:useBean id="SearchSedeListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Sede.do"><dhv:label name="anagrafica.sede">Sede</dhv:label></a> > 
<dhv:label name="anagrafica.risultati">Risultati ricerca</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="SearchSedeListInfo"/></center>
</dhv:include>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchSedeListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    
    <th nowrap <% ++columnCount; %>>
      <strong><a href="Sede.do?command=Search&column=i.comune"><dhv:label name="">Comune</dhv:label></a></strong>
      <%= SearchSedeListInfo.getSortIcon("i.comune") %>
    </th>
		 <th nowrap <% ++columnCount; %>>
          <strong>Cap</strong>
		</th>

		<th nowrap <% ++columnCount; %>>
          <strong><dhv:label name="">Via</dhv:label></strong>
        </th>
        
        <th nowrap <% ++columnCount; %>>
          <strong><dhv:label name="">Latitudine</dhv:label></strong>
        </th>
        
        <th nowrap <% ++columnCount; %>>
          <strong><dhv:label name="">Longitudine</dhv:label></strong>
        </th>
  </tr>
<%
	Iterator j = sedeList.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Indirizzo thisS = (Indirizzo)j.next();
    
%>


  <tr class="row<%= rowid %>">
    
	<td nowrap>

    <%String comune = ( ComuniAnagrafica.isInteger(thisS.getComune()))?
    		ComuniList.getSelectedValue(thisS.getComune()):
    			thisS.getComune(); %>
	
		<dhv:evaluate if="<%= !isPopup(request) %>">
	     <a href="Sede.do?command=Details&sedeId=<%=thisS.getIdIndirizzo()%>"><%=comune%></a>
	</dhv:evaluate>
	<dhv:evaluate if="<%= isPopup(request) %>">
     	     <a href="Sede.do?command=ScegliSede&sedeId=<%=thisS.getIdIndirizzo()%>&tipologia=<%=request.getParameter("tipologia") %>&popup=true">
     	     <%= comune %></a>
    </dhv:evaluate>

	       
	</td>
	
	<td nowrap>
       <%= toHtml("(" + thisS.getCap() + ")") %>
       </td>
		<td nowrap>
       <%= toHtml(thisS.getVia()) %>
       </td>
       <td nowrap>
       <%= (thisS.getLatitudine()) %>
       </td>
      <td nowrap>
       <%= (thisS.getLongitudine()) %>
       </td>

  </tr>
<%}%>
<%} else {%>

<%}%>
</table>
<br />
<%= addHiddenParams(request, "popup|popupType|actionId") %>
<dhv:pagedListControl object="SearchSedeListInfo" tdClass="row1"/>

