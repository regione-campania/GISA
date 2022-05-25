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



<%@page import="org.aspcfs.modules.buffer.base.Comune"%><jsp:useBean id="BufferList" class="org.aspcfs.modules.buffer.base.BufferList" scope="request"/>
<jsp:useBean id="BufferListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

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
Buffer >
<a href="Buffer.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<dhv:label name="vigilanza">Lista Buffer</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
 
   
  <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="BufferListInfo"/>
 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
	 
	   
	   	<th>Codice Buffer</th>
	   	<th>Descrizione Breve</th>
      	<th>Data Evento</th>
      	<th>Comuni Coinvolti</th>
      	<th>Stato</th>

  </tr>
  <%
    Iterator j = BufferList.iterator();
    if ( j.hasNext() ) {
      int rowid = 0;
      int i =0;
      while (j.hasNext()) {
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.buffer.base.Buffer thisBuffer = ( org.aspcfs.modules.buffer.base.Buffer )j.next();
  %>
    <tr class="row<%= rowid %>">
    
     	<td width="10%" valign="top" nowrap>
			<a href="Buffer.do?command=Details&id=<%= thisBuffer.getId() %>">
			<%=thisBuffer.getCodiceBuffer() %>
     		</a>
		</td>
		
		<td width="10%" valign="top" nowrap> <%=thisBuffer.getDescrizioneBreve() %> </td>
		<td width="10%" valign="top" nowrap> <%=toDateString(thisBuffer.getDataEvento()) %> </td>
				<td width="10%" valign="top" nowrap> 
				<%
				for (Comune c : thisBuffer.getListaComuni())
				{
					out.println("- "+c.getDescrizione()+"<br>");
				}
				%>
				 </td>
		
		<td width="10%" valign="top" nowrap> <%=thisBuffer.getDescrStato() %> </td>
		
		
    	</tr>
      

  <%}%>
  <%} else {%>
  
    <tr class="containerBody">
      <td colspan="7">
        <dhv:label name="accounts.richieste.search.notFound">Nessun Buffer Trovato.</dhv:label>
      </td>
    </tr>
  <%}%>
  </table>
  
  <dhv:pagedListControl object="BufferListInfo"/>
