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
  - Version: $Id: accounts_details.jsp 19045 2007-02-07 18:06:22Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<%@page import="org.aspcfs.modules.molluschibivalvi.base.HistoryClassificazione"%>
<%@page import="java.util.Date"%>
<%@page import="org.aspcfs.modules.molluschibivalvi.base.Concessione"%><jsp:useBean id="SearchHistoryOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>

<%@page import="org.aspcfs.modules.molluschibivalvi.base.ConcessioniList"%><jsp:useBean id="ZoneProduzione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Classificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="orgId" class="java.lang.String" scope="request" />


<%@ include file="../initPage.jsp"%>


<% String param1 = "orgId=" + orgId;
%>
<dhv:container name="molluschibivalvi" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>


<%
ConcessioniList list = (ConcessioniList) request.getAttribute("HistoryConcessioni");
int id = list.getIdZona() ;

%>

<dhv:evaluate if="<%= !isPopup(request) %>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td><a href="MolluschiBivalvi.do">Molluschi bivalvi</a> > <%
			if (request.getParameter("return") == null) {
			%>
			<a href="MolluschiBivalvi.do?command=Search"><dhv:label
				name="stabilimenti.SearchResults">Search Results</dhv:label></a> > <%
			} 
			%>
			
			
			<a href="MolluschiBivalvi.do?command=Details&orgId=<%= id %>">Scheda Molluschi Bivalvi</a> > History Concessioni
			
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>


<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchHistoryOrgListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th nowrap  <% ++columnCount; %> >
         <strong>Ragione Sociale Concessionario</strong>
	</th>
	<th  <% ++columnCount; %>>
         Num Concessione
    </th> 
    <th nowrap  <% ++columnCount; %> >
      <strong>Data Concessione </strong>
    </th>
   <th nowrap  <% ++columnCount; %> >
	  <strong>Data Scadenza Concessione</strong>
	</th>
	 <th nowrap  <% ++columnCount; %> >
	  <strong>Data Sospensione</strong>
	</th>
	 <th nowrap  <% ++columnCount; %> >
	  <strong>&nbsp;</strong>
	</th>
	
  </tr>
<%
	Iterator j = list.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Concessione thisOrg = (Concessione)j.next();
%>
  <tr class="row<%= rowid %>">
    <td>
    <%=thisOrg.getConcessionario().getName() %>
	</td>
	
	<td><%=thisOrg.getNumConcessione() %></td>	
	<td><%=thisOrg.getDataConcessioneasString() %></td>	
	<td><%=thisOrg.getDataScadenzaasString() %></td>	
	<td><%=thisOrg.getDataSospensioneasString() %></td>	
	<td><%=(thisOrg.isEnabled()==false && thisOrg.getDataSospensione()==null)? "IN CONCESSIONE" : (thisOrg.getDataSospensione()!=null) ? "SOSPESA PER "+Classificazione.getSelectedValue(thisOrg.getIdSospensione()) : "IN CONCESSIONE"  %></td>
	
	
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="8">
    
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchHistoryOrgListInfo" tdClass="row1"/>
</dhv:container>